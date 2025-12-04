import React, { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import { Building, Users, Star, FileText, Edit, TrendingUp, Printer, User, Lock, Save, X } from 'lucide-react'; 
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import './styles.css';
import { gerarHtmlRelatorio, gerarHtmlRelatorioHistorico } from '../../utils/reportTemplate';
import * as api from '../../services/api';

const formatarDataBrasileira = (dataString) => {
    if (!dataString) return '';
    try {
        const [ano, mes, dia] = dataString.split('-');
        return `${dia}/${mes}/${ano}`;
    } catch (e) { return dataString; }
};

const Sidebar = ({ activeTab, onTabChange }) => (
    <div className="sidebar">
        <div className="sidebar-header"><h2>Dashboard</h2></div>
        <nav className="sidebar-nav">
            <button className={`sidebar-item ${activeTab === 'informacoes' ? 'active' : ''}`} onClick={() => onTabChange('informacoes')}><Building className="sidebar-icon" size={20} /><span className="sidebar-text">Informações</span></button>
            <button className={`sidebar-item ${activeTab === 'estagiarios' ? 'active' : ''}`} onClick={() => onTabChange('estagiarios')}><Users className="sidebar-icon" size={20} /><span className="sidebar-text">Estagiários</span></button>
            <button className={`sidebar-item ${activeTab === 'avaliacoes' ? 'active' : ''}`} onClick={() => onTabChange('avaliacoes')}><Star className="sidebar-icon" size={20} /><span className="sidebar-text">Avaliações</span></button>
            <button className={`sidebar-item ${activeTab === 'relatorios' ? 'active' : ''}`} onClick={() => onTabChange('relatorios')}><FileText className="sidebar-icon" size={20} /><span className="sidebar-text">Relatórios</span></button>
        </nav>
    </div>
);

const Header = ({ userName }) => (
    <header className="dashboard-header-component">
        <div className="header-left"><h1>Bem-vindo, <span className="highlight">{userName}</span></h1><p>Aqui está o resumo das atividades da sua instituição</p></div>
    </header>
);

const StatsCard = ({ dashboardData }) => (
    <div className="stats-overview">
        <div className="stat-card"><div className="stat-icon primary"><Users size={24} /></div><div className="stat-info"><h3>{dashboardData?.totalEstagiarios || 0}</h3><p>Estagiários</p></div></div>
        <div className="stat-card"><div className="stat-icon secondary"><Building size={24} /></div><div className="stat-info"><h3>{dashboardData?.totalEmpresasParceiras || 0}</h3><p>Empresas</p></div></div>
        <div className="stat-card"><div className="stat-icon success"><Star size={24} /></div><div className="stat-info"><h3>{dashboardData?.totalAvaliacoesRecebidas || 0}</h3><p>Avaliações</p></div></div>
        <div className="stat-card"><div className="stat-icon warning"><TrendingUp size={24} /></div><div className="stat-info"><h3>{dashboardData?.mediaGeralNotas || 0}</h3><p>Média geral</p></div></div>
    </div>
);

const AbaInformacoes = ({ dadosFaculdade, emailCoordenador, onEditProfile }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header"><h3>Informações da instituição</h3><button className="btn-primary" onClick={onEditProfile}><Edit className="btn-icon" size={16} /><span className="btn-text">Editar perfil</span></button></div>
                <div className="card-body">
                    <div className="info-details">
                        <div className="info-row"><span className="info-label">Nome:</span><span className="info-value">{dadosFaculdade.nome}</span></div>
                        <div className="info-row"><span className="info-label">CNPJ:</span><span className="info-value">{dadosFaculdade.cnpj}</span></div>
                        <div className="info-row"><span className="info-label">Telefone:</span><span className="info-value">{dadosFaculdade.telefone}</span></div>
                        <div className="info-row"><span className="info-label">Email (Resp.):</span><span className="info-value">{emailCoordenador}</span></div>
                        <div className="info-row"><span className="info-label">Site:</span><span className="info-value">{dadosFaculdade.site || 'Não informado'}</span></div>
                        
                        {/* Exibição do endereço tratada */}
                        <div className="info-row">
                            <span className="info-label">Endereço:</span>
                            <span className="info-value">
                                {dadosFaculdade.endereco ? (
                                    `${dadosFaculdade.endereco.logradouro || ''}, ${dadosFaculdade.endereco.numero || ''} - ${dadosFaculdade.endereco.bairro || ''} - ${dadosFaculdade.endereco.cidade || ''}/${dadosFaculdade.endereco.estados || ''}`
                                ) : 'Endereço não informado'}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaEstagiarios = ({ estagiarios }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header"><h3>Estagiários cadastrados</h3></div>
                <div className="card-body">
                    <div className="table-container">
                        <table className="data-table">
                            <thead><tr><th>Nome</th><th>Curso</th><th>Período</th><th>Empresa</th><th>Status</th></tr></thead>
                            <tbody>
                                {estagiarios && estagiarios.length > 0 ? estagiarios.map(est => (
                                    <tr key={est.id}>
                                        <td>{est.nome}</td>
                                        <td>{est.dadosAcademicos?.curso}</td>
                                        <td>{est.dadosAcademicos?.periodoSemestre}</td>
                                        <td>{est.empresa?.nome}</td>
                                        <td><span className="status-badge active">Ativo</span></td>
                                    </tr>
                                )) : <tr><td colSpan="5" style={{textAlign:'center', padding:'1rem'}}>Nenhum estagiário.</td></tr>}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaAvaliacoes = ({ estagiarios }) => {
    const [idSelecionado, setIdSelecionado] = useState(null);
    const [carregando, setCarregando] = useState(false);
    const [avaliacoes, setAvaliacoes] = useState([]);

    const handleMudarEstagiario = async (e) => {
        const id = e.target.value ? parseInt(e.target.value) : null;
        setIdSelecionado(id);
        if (!id) { setAvaliacoes([]); return; }
        try {
            setCarregando(true);
            const data = await api.getEstagiarioDetails(id);
            setAvaliacoes(data.avaliacoes || []);
        } catch (error) { console.error("Erro:", error); } finally { setCarregando(false); }
    };

    return (
        <div className="tab-content">
            <div className="info-grid">
                <div className="info-card">
                    <div className="card-header"><h3>Avaliações dos estagiários</h3></div>
                    <div className="card-body">
                        <div className="form-group">
                            <label>Selecione um estagiário:</label>
                            <select value={idSelecionado || ''} onChange={handleMudarEstagiario} className="form-select">
                                <option value="">-- Selecione --</option>
                                {estagiarios && estagiarios.map(est => (<option key={est.id} value={est.id}>{est.nome} - {est.dadosAcademicos?.curso}</option>))}
                            </select>
                        </div>
                        {carregando ? <div style={{textAlign:'center'}}><p>Carregando...</p></div> : idSelecionado ? (
                            <div className="evaluations-list">
                                {avaliacoes.length > 0 ? avaliacoes.map(av => (
                                    <div key={av.id} className="evaluation-card">
                                        <div className="evaluation-header">
                                            <div className="evaluation-author"><h5>{av.supervisor?.nome}</h5><p className="evaluation-company">{av.supervisor?.empresa?.nome}</p></div>
                                            <div className="evaluation-meta"><span className="evaluation-date">{formatarDataBrasileira(av.dataAvaliacao)}</span><div className="evaluation-rating"><small>Nota: {av.notaDesempenho}/5</small></div></div>
                                        </div>
                                        <p className="evaluation-text">{av.feedback}</p>
                                    </div>
                                )) : <div style={{textAlign:'center'}}><p>Nenhuma avaliação.</p></div>}
                            </div>
                        ) : <div style={{textAlign:'center'}}><p>Selecione um estagiário.</p></div>}
                    </div>
                </div>
            </div>
        </div>
    );
};

const AbaRelatorios = ({ estagiarios }) => {
    const [idSelecionado, setIdSelecionado] = useState(null);
    const [relatorios, setRelatorios] = useState([]);
    const [competenciasAluno, setCompetenciasAluno] = useState([]);
    const [dadosEstagioAluno, setDadosEstagioAluno] = useState(null);
    const [estagiarioCompleto, setEstagiarioCompleto] = useState(null);
    const [carregando, setCarregando] = useState(false);

    const handleMudarEstagiario = async (e) => {
        const id = e.target.value ? parseInt(e.target.value) : null;
        setIdSelecionado(id);
        if (!id) { setRelatorios([]); setCompetenciasAluno([]); setDadosEstagioAluno(null); setEstagiarioCompleto(null); return; }
        try {
            setCarregando(true);
            const dadosEstagiario = await api.getEstagiarioDetails(id);
            setEstagiarioCompleto(dadosEstagiario);
            const listaAvaliacoes = dadosEstagiario.avaliacoes || [];
            listaAvaliacoes.sort((a, b) => b.id - a.id);
            setRelatorios(listaAvaliacoes);
            setDadosEstagioAluno(dadosEstagiario.dadosEstagio);
            const dadosCompetencias = await api.getCompetencias(id);
            setCompetenciasAluno(dadosCompetencias || []);
        } catch (error) { console.error("Erro:", error); } finally { setCarregando(false); }
    };

    const abrirRelatorioIndividual = (relatorio, imprimir = false) => {
        const win = window.open('', '_blank', 'height=900,width=1000,scrollbars=yes');
        const htmlContent = gerarHtmlRelatorio(relatorio, dadosEstagioAluno, competenciasAluno);
        win.document.write(htmlContent);
        win.document.close();
        if (imprimir) { setTimeout(() => { win.focus(); win.print(); }, 800); }
    };

    const abrirRelatorioHistorico = () => {
        if (!estagiarioCompleto) return;
        const win = window.open('', '_blank', 'height=900,width=1000,scrollbars=yes');
        const htmlContent = gerarHtmlRelatorioHistorico(estagiarioCompleto, relatorios, competenciasAluno, dadosEstagioAluno);
        win.document.write(htmlContent);
        win.document.close();
    };

    return (
        <div className="tab-content">
            <div className="info-card">
                <div className="card-header"><h3>Central de relatórios</h3></div>
                <div className="card-body">
                    <div className="form-group" style={{display: 'flex', gap: '10px', alignItems: 'flex-end'}}>
                        <div style={{flex: 1}}>
                            <label>Selecione o aluno:</label>
                            <select value={idSelecionado||''} onChange={handleMudarEstagiario} className="form-select"><option value="">-- Selecione --</option>{estagiarios && estagiarios.map(e=><option key={e.id} value={e.id}>{e.nome}</option>)}</select>
                        </div>
                        {idSelecionado && (
                            <button onClick={abrirRelatorioHistorico} className="btn-primary" style={{backgroundColor: '#2C5282', borderColor: '#2C5282', height: '42px', display: 'flex', alignItems: 'center', gap: '5px'}}>
                                <FileText size={18}/> Histórico completo
                            </button>
                        )}
                    </div>
                    {carregando ? <div style={{textAlign:'center', padding:'2rem'}}><p>Carregando documentos...</p></div> : idSelecionado && relatorios.length > 0 ? (
                        <div className="table-container">
                            <h4 style={{marginTop:'20px', marginBottom:'10px', color:'#4A5568'}}>Avaliações Individuais</h4>
                            <table className="data-table">
                                <thead><tr><th>Data</th><th>Supervisor</th><th>Ações</th></tr></thead>
                                <tbody>
                                    {relatorios.map(rel => (
                                        <tr key={rel.id}>
                                            <td>{formatarDataBrasileira(rel.dataAvaliacao)}</td>
                                            <td>{rel.supervisor?.nome}</td>
                                            <td style={{display:'flex', gap:'8px'}}>
                                                <button className="btn-secondary" onClick={() => abrirRelatorioIndividual(rel, false)} style={{padding:'0.4rem 0.8rem', fontSize:'0.8rem'}}>Ler</button>
                                                <button className="btn-primary" onClick={() => abrirRelatorioIndividual(rel, true)} style={{padding:'0.4rem 0.8rem', fontSize:'0.8rem', backgroundColor:'#48bb78', borderColor:'#48bb78'}}>Imprimir</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    ) : <div style={{textAlign:'center', padding:'2rem'}}><p style={{fontStyle:'italic', color:'#718096'}}>Selecione um aluno.</p></div>}
                </div>
            </div>
        </div>
    );
};

const ModalPerfil = ({ onClose, dadosIniciais, onUpdateSuccess }) => {
    const [formData, setFormData] = useState({ 
        nome: dadosIniciais.dadosCoordenador?.nome || '', 
        email: dadosIniciais.dadosCoordenador?.email || '', 
        senha: '' 
    });
    const [salvando, setSalvando] = useState(false);
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();
    
    const handleChange = (e) => setFormData({...formData, [e.target.name]: e.target.value});
    
    const handleSubmit = async (e) => { 
        e.preventDefault(); 
        setSalvando(true);
        try { 
            const idCoordenador = dadosIniciais.dadosCoordenador?.id;
            await api.atualizarCoordenador(idCoordenador, { 
                ...formData, 
                faculdadeCnpj: dadosIniciais.dadosFaculdade.cnpj 
            }); 
            exibirMensagem('Perfil atualizado com sucesso!', 'sucesso'); 
            setTimeout(() => { onUpdateSuccess(); onClose(); }, 1500); 
        } catch (error) { 
            exibirMensagem('Erro ao atualizar perfil.', 'erro'); 
            setSalvando(false);
        } 
    };

    return (
        <div className="evaluation-form-overlay">
            <MensagemFeedback mensagem={mensagem} tipo={tipoMensagem} visivel={visivel} onClose={fecharMensagem} />
            <div className="evaluation-form-modal">
                <div className="form-header">
                    <h3>Editar perfil (Coordenador)</h3>
                    <button className="close-btn" onClick={onClose}><X size={20}/></button>
                </div>
                <form onSubmit={handleSubmit} className="form-body">
                    <div className="form-group"><label>Nome completo</label><input className="form-input" name="nome" value={formData.nome} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Email institucional</label><input className="form-input" name="email" value={formData.email} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Nova senha <small style={{color:'#718096'}}>(Opcional)</small></label><input className="form-input" name="senha" type="password" value={formData.senha} onChange={handleChange} placeholder="••••••••" /></div>
                    <div className="form-actions"><button type="button" className="btn-secondary" onClick={onClose} disabled={salvando}>Cancelar</button><button type="submit" className="btn-primary" disabled={salvando}>{salvando ? 'Salvando...' : 'Salvar'}</button></div>
                </form>
            </div>
        </div>
    );
};

function DashboardFaculdade({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [dadosDashboard, setDadosDashboard] = useState(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState('');
    const [activeTab, setActiveTab] = useState(initialTab);
    const [showProfileModal, setShowProfileModal] = useState(false);

    const handleTabChange = (newTab) => { setActiveTab(newTab); const searchParams = new URLSearchParams(location.search); searchParams.set('tab', newTab); navigate({ search: searchParams.toString() }); };
    const buscarDados = useCallback(async () => { if (!user) return; try { setCarregando(true); const data = await api.getDashboardData(); setDadosDashboard(data.dashboardCoordenador); } catch (err) { setErro(err.message || 'Falha ao carregar os dados.'); } finally { setCarregando(false); } }, [user]);
    useEffect(() => { buscarDados(); }, [buscarDados]);

    if (carregando) return <div className="dashboard-layout"><div className="loading">Carregando...</div></div>;
    if (erro) return <div className="dashboard-layout"><div className="error">{erro}</div></div>;
    if (!dadosDashboard || !dadosDashboard.dadosFaculdade) return <div className="dashboard-layout"><div className="no-data">Nenhum dado encontrado.</div></div>;

    return (
        <div className="dashboard-layout">
            <Sidebar activeTab={activeTab} onTabChange={handleTabChange} />
            <div className="dashboard-main">
                <Header userName={dadosDashboard.dadosFaculdade.nome} />
                <StatsCard dashboardData={dadosDashboard} />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosFaculdade={dadosDashboard.dadosFaculdade} emailCoordenador={dadosDashboard.dadosCoordenador?.email} onEditProfile={() => setShowProfileModal(true)} />}
                    {activeTab === 'estagiarios' && <AbaEstagiarios estagiarios={dadosDashboard.estagiarios} />}
                    {activeTab === 'avaliacoes' && <AbaAvaliacoes estagiarios={dadosDashboard.estagiarios} />}
                    {activeTab === 'relatorios' && <AbaRelatorios estagiarios={dadosDashboard.estagiarios} />}
                </div>
            </div>
            {showProfileModal && <ModalPerfil onClose={() => setShowProfileModal(false)} dadosIniciais={dadosDashboard} onUpdateSuccess={buscarDados} />}
        </div>
    );
}
export default DashboardFaculdade;