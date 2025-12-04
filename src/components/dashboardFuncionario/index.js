import React, { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import { Building, Users, Star, FileText, Edit, Trash2, PenTool, Rocket, Briefcase, CheckCircle, AlertTriangle, User, Lock, Save, X } from 'lucide-react'; 
import FormularioAvaliacao from '../formularioAvaliacao'; // Certifique-se que o caminho está certo
import ModalCompetencias from '../competencias'; 
import ModalDadosEstagio from '../dadosEstagio'; 
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
        <div className="header-left"><h1>Bem-vindo, <span className="highlight">{userName}</span></h1><p>Gerencie sua equipe e avaliações</p></div>
    </header>
);

const StatsCard = ({ dashboardData }) => (
    <div className="stats-overview">
        <div className="stat-card"><div className="stat-icon primary"><Users size={24} /></div><div className="stat-info"><h3>{dashboardData?.totalEstagiarios || dashboardData?.estagiarios?.length || 0}</h3><p>Estagiários</p></div></div>
        <div className="stat-card"><div className="stat-icon success"><Star size={24} /></div><div className="stat-info"><h3>{dashboardData?.totalAvaliacoes || 0}</h3><p>Avaliações Feitas</p></div></div>
        <div className="stat-card"><div className="stat-icon secondary"><Building size={24} /></div><div className="stat-info"><h3>Empresa</h3><p>{dashboardData?.dadosSupervisor?.empresa?.nome || 'Minha empresa'}</p></div></div>
    </div>
);

const AbaInformacoes = ({ dadosFuncionario, onEditProfile }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header"><h3>Informações pessoais</h3><button className="btn-primary" onClick={onEditProfile}><Edit className="btn-icon" size={16} /><span className="btn-text">Editar perfil</span></button></div>
                <div className="card-body">
                    <div className="info-details">
                        <div className="info-row"><span className="info-label">Nome:</span><span className="info-value">{dadosFuncionario.nome}</span></div>
                        <div className="info-row"><span className="info-label">Cargo:</span><span className="info-value">{dadosFuncionario.cargo}</span></div>
                        <div className="info-row"><span className="info-label">Email:</span><span className="info-value">{dadosFuncionario.email}</span></div>
                        <div className="info-row"><span className="info-label">Empresa:</span><span className="info-value">{dadosFuncionario.empresa?.nome}</span></div>
                        <div className="info-row"><span className="info-label">CNPJ:</span><span className="info-value">{dadosFuncionario.empresa?.cnpj}</span></div>
                        <div className="info-row" style={{backgroundColor: '#EBF8FF', padding: '10px', borderRadius: '6px', border: '1px solid #BEE3F8', marginTop: '10px'}}>
                            <span className="info-label" style={{color: '#2C5282', display:'flex', alignItems:'center', gap:'5px'}}><Building size={16}/> Código da empresa:</span>
                            <span className="info-value" style={{fontWeight: '700', fontSize: '1.2rem', color: '#2B6CB0', letterSpacing: '1px'}}>{dadosFuncionario.empresa?.codigoEmpresa}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaEstagiarios = ({ estagiarios, aoExcluirEstagiario, aoAvaliarEstagiario, aoGerenciarCompetencias, aoEditarDadosEstagio }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header"><h3>Estagiários sob sua supervisão</h3></div>
                <div className="card-body">
                    <div className="table-container">
                        <table className="data-table">
                            <thead><tr><th>Nome</th><th>Curso</th><th>Instituição</th><th>Contrato</th><th>Ações</th></tr></thead>
                            <tbody>
                                {estagiarios && estagiarios.length > 0 ? (
                                    estagiarios.map(estagiario => {
                                        const temDados = estagiario.dadosEstagio && estagiario.dadosEstagio.id;
                                        return (
                                            <tr key={estagiario.id}>
                                                <td>{estagiario.nome}</td>
                                                <td>{estagiario.dadosAcademicos?.curso}</td>
                                                <td>{estagiario.dadosAcademicos?.faculdade?.nome}</td>
                                                <td>{temDados ? (<span className="badge-status ok"><CheckCircle size={14}/> Vigente</span>) : (<span className="badge-status pendente"><AlertTriangle size={14}/> Pendente</span>)}</td>
                                                <td style={{ display: 'flex', gap: '8px' }}>
                                                    <button className={temDados ? "btn-dados-ok-solid" : "btn-dados-pendente-solid"} onClick={() => aoEditarDadosEstagio(estagiario)} title={temDados ? "Visualizar Contrato" : "URGENTE: Preencher Dados"}><Briefcase size={14} style={{marginRight: '4px'}}/> {temDados ? "Dados" : "Preencher"}</button>
                                                    <button className="btn-skills-solid" onClick={() => aoGerenciarCompetencias(estagiario)} title="Skills"><Rocket size={14} style={{marginRight: '4px'}}/> Skills</button>
                                                    <button className="btn-primary" onClick={() => aoAvaliarEstagiario(estagiario)} style={{ padding: '0.4rem 0.8rem', fontSize: '0.8rem' }} title="Avaliar"><PenTool size={14} style={{marginRight: '4px'}}/> Avaliar</button>
                                                    <button className="btn-secondary" onClick={() => { if(window.confirm(`Excluir ${estagiario.nome}?`)) aoExcluirEstagiario(estagiario.id); }} style={{ color: '#e53e3e', borderColor: '#e53e3e', padding: '0.4rem 0.8rem', fontSize: '0.8rem' }} title="Excluir"><Trash2 size={14} /></button>
                                                </td>
                                            </tr>
                                        );
                                    })
                                ) : ( <tr><td colSpan="5" style={{textAlign: 'center', padding: '1rem'}}>Nenhum estagiário encontrado.</td></tr> )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaAvaliacoes = ({ estagiarios, aoEditarAvaliacao, aoExcluirAvaliacao }) => {
    const [idSelecionado, setIdSelecionado] = useState(null);
    const [carregando, setCarregando] = useState(false);
    const [avaliacoes, setAvaliacoes] = useState([]);

    const handleMudarEstagiario = async (e) => {
        const id = e.target.value ? parseInt(e.target.value) : null;
        setIdSelecionado(id);
        if (!id) { setAvaliacoes([]); return; }
        try {
            setCarregando(true);
            const dados = await api.getEstagiarioDetails(id);
            const lista = dados.avaliacoes || [];
            lista.sort((a, b) => b.id - a.id);
            setAvaliacoes(lista);
        } catch (error) { console.error("Erro:", error); } finally { setCarregando(false); }
    };

    const renderEstrelas = (nota) => {
        return (
            <div style={{display: 'flex', flexDirection: 'row', gap: '2px', alignItems: 'center'}}>
                {[1, 2, 3, 4, 5].map((star) => (
                    <Star 
                        key={star} 
                        size={14} 
                        fill={star <= nota ? "#ed8936" : "none"} 
                        color={star <= nota ? "#ed8936" : "#cbd5e0"} 
                    />
                ))}
            </div>
        );
    };

    return (
        <div className="tab-content">
            <div className="info-card">
                <div className="card-header"><h3>Histórico de avaliações</h3></div>
                <div className="card-body">
                    <div className="form-group">
                        <label>Selecione um estagiário:</label>
                        <select value={idSelecionado || ''} onChange={handleMudarEstagiario} className="form-select">
                            <option value="">-- Selecione --</option>
                            {estagiarios && estagiarios.map(est => (<option key={est.id} value={est.id}>{est.nome}</option>))}
                        </select>
                    </div>
                    {carregando ? <div style={{textAlign:'center', padding:'2rem'}}><p>Carregando...</p></div> : idSelecionado ? (
                        <div className="evaluations-list">
                            {avaliacoes.length > 0 ? (
                                avaliacoes.map(av => (
                                    <div key={av.id} className="evaluation-card">
                                        <div className="evaluation-header">
                                            <div className="evaluation-author">
                                                <h5>{av.titulo || 'Avaliação'}</h5>
                                                <div className="evaluation-meta">
                                                    <span className="evaluation-date">{formatarDataBrasileira(av.dataAvaliacao)}</span>
                                                    {renderEstrelas(av.notaDesempenho)}
                                                </div>
                                            </div>
                                            <div style={{display:'flex', gap:'8px'}}>
                                                <button onClick={() => aoEditarAvaliacao(av, idSelecionado)} className="btn-secondary" style={{padding:'5px', minWidth:'auto'}} title="Editar"><Edit size={16} /></button>
                                                <button onClick={() => { if(window.confirm('Tem certeza que deseja excluir esta avaliação?')) aoExcluirAvaliacao(av.id, idSelecionado); }} className="btn-secondary" style={{padding:'5px', minWidth:'auto', color:'#e53e3e', borderColor:'#e53e3e'}} title="Excluir"><Trash2 size={16} /></button>
                                            </div>
                                        </div>
                                        <p className="evaluation-text">{av.feedback}</p>
                                    </div>
                                ))
                            ) : <div style={{textAlign:'center', padding:'1rem'}}><p>Nenhuma avaliação encontrada.</p></div>}
                        </div>
                    ) : <div style={{textAlign:'center', padding:'2rem'}}><p style={{fontStyle:'italic', color:'#718096'}}>Selecione um estagiário.</p></div>}
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
                            <h4 style={{marginTop:'20px', marginBottom:'10px', color:'#4A5568'}}>Avaliações individuais</h4>
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

const ModalPerfil = ({ onClose, dadosIniciais, aoAtualizarSucesso }) => {
    const [formData, setFormData] = useState({ 
        nome: dadosIniciais.nome || '', 
        email: dadosIniciais.email || '', 
        cargo: dadosIniciais.cargo || 'SUPERVISOR', 
        senha: '' 
    });
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleSubmit = async (e) => { 
        e.preventDefault(); 
        try { 
            await api.atualizarSupervisor(dadosIniciais.id, { 
                empresaCnpj: dadosIniciais.empresa?.cnpj, 
                ...formData 
            }); 
            exibirMensagem('Perfil atualizado!', 'sucesso'); 
            setTimeout(() => { aoAtualizarSucesso(); onClose(); }, 1500); 
        } catch (error) { 
            exibirMensagem('Erro ao atualizar.', 'erro'); 
        } 
    };

    return (
        <div className="evaluation-form-overlay">
            <MensagemFeedback mensagem={mensagem} tipo={tipoMensagem} visivel={visivel} onClose={fecharMensagem} />
            <div className="evaluation-form-modal">
                <div className="form-header"><h3>Editar perfil</h3><button className="close-btn" onClick={onClose}>×</button></div>
                <form onSubmit={handleSubmit} className="form-body">
                    <div className="form-group"><label>Nome</label><input className="form-input" name="nome" value={formData.nome} onChange={e=>setFormData({...formData,nome:e.target.value})} /></div>
                    <div className="form-group"><label>Email</label><input className="form-input" name="email" value={formData.email} onChange={e=>setFormData({...formData,email:e.target.value})} /></div>
                    <div className="form-group"><label>Cargo</label><select name="cargo" value={formData.cargo} onChange={e=>setFormData({...formData,cargo:e.target.value})} className="form-input"><option value="SUPERVISOR">Supervisor</option><option value="GERENTE">Gerente</option></select></div>
                    <div className="form-group"><label>Nova senha</label><input className="form-input" name="senha" type="password" value={formData.senha} onChange={e=>setFormData({...formData,senha:e.target.value})} placeholder="********" /></div>
                    <div className="form-actions"><button type="button" className="btn-secondary" onClick={onClose}>Cancelar</button><button type="submit" className="btn-primary">Salvar</button></div>
                </form>
            </div>
        </div>
    );
};

function DashboardFuncionario({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    
    const [dadosDashboard, setDadosDashboard] = useState(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState('');
    const [activeTab, setActiveTab] = useState(initialTab);
    const [showProfileModal, setShowProfileModal] = useState(false);
    
    const [modalAvaliacaoAberto, setModalAvaliacaoAberto] = useState(false);
    const [estagiarioParaAvaliar, setEstagiarioParaAvaliar] = useState(null);
    const [avaliacaoEmEdicao, setAvaliacaoEmEdicao] = useState(null);
    const [submetendoAvaliacao, setSubmetendoAvaliacao] = useState(false);

    const [modalCompetenciasAberto, setModalCompetenciasAberto] = useState(false);
    const [estagiarioParaCompetencias, setEstagiarioParaCompetencias] = useState(null);

    const [modalDadosAberto, setModalDadosAberto] = useState(false);
    const [estagiarioParaDados, setEstagiarioParaDados] = useState(null);

    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    const handleTabChange = (newTab) => { setActiveTab(newTab); const searchParams = new URLSearchParams(location.search); searchParams.set('tab', newTab); navigate({ search: searchParams.toString() }); };

    const carregarDados = useCallback(async () => {
        if (!user) return;
        try { setCarregando(true); const data = await api.getDashboardData(); setDadosDashboard(data.dashboardSupervisor); } catch (err) { setErro(err.message); } finally { setCarregando(false); }
    }, [user]);

    useEffect(() => { carregarDados(); }, [carregarDados]);

    const handleExcluirEstagiario = async (id) => { try { await api.excluirEstagiario(id); exibirMensagem('Excluído!', 'sucesso'); carregarDados(); } catch (error) { exibirMensagem('Erro.', 'erro'); } };

    const abrirModalAvaliacao = (est) => { 
        setEstagiarioParaAvaliar(est); 
        setAvaliacaoEmEdicao(null); 
        setModalAvaliacaoAberto(true); 
    };

    const abrirModalEdicaoAvaliacao = (avaliacao, idEstagiarioAtual) => {
        const estagiarioMock = { 
            id: idEstagiarioAtual || avaliacao.estagiarioId,
            nome: dadosDashboard?.estagiarios?.find(e => e.id === idEstagiarioAtual)?.nome || 'Estagiário' 
        };
        
        setEstagiarioParaAvaliar(estagiarioMock); 
        setAvaliacaoEmEdicao(avaliacao); 
        setModalAvaliacaoAberto(true);
    };

    const handleExcluirAvaliacao = async (idAvaliacao, idEstagiario) => {
        try {
            await api.excluirAvaliacao(idAvaliacao);
            exibirMensagem('Avaliação excluída!', 'sucesso');
            carregarDados();
        } catch (error) {
            exibirMensagem('Erro ao excluir.', 'erro');
        }
    };

    const salvarAvaliacao = async (dados) => { 
        setSubmetendoAvaliacao(true); 
        try { 
            if (avaliacaoEmEdicao) {
                await api.atualizarAvaliacao(avaliacaoEmEdicao.id, {
                    ...dados,
                    estagiarioId: estagiarioParaAvaliar.id
                });
                exibirMensagem('Avaliação atualizada!', 'sucesso');
            } else {
                await api.criarAvaliacao({
                    ...dados, 
                    titulo: "Avaliação", 
                    dataAvaliacao: new Date().toISOString().split('T')[0]
                });
                exibirMensagem('Avaliação criada!', 'sucesso');
            }
            setModalAvaliacaoAberto(false); 
            carregarDados(); 
        } catch (e) { 
            exibirMensagem(e.message, 'erro'); 
        } finally { 
            setSubmetendoAvaliacao(false); 
        } 
    };

    const abrirModalCompetencias = (est) => { setEstagiarioParaCompetencias(est); setModalCompetenciasAberto(true); };
    const abrirModalDadosEstagio = (est) => { setEstagiarioParaDados(est); setModalDadosAberto(true); };

    if (carregando) return <div className="dashboard-layout"><div className="loading">Carregando...</div></div>;
    if (erro) return <div className="dashboard-layout"><div className="error">{erro}</div></div>;
    if (!dadosDashboard) return <div className="dashboard-layout"><div className="no-data">Nenhum dado.</div></div>;

    return (
        <div className="dashboard-layout">
            <MensagemFeedback mensagem={mensagem} tipo={tipoMensagem} visivel={visivel} onClose={fecharMensagem} />
            <Sidebar activeTab={activeTab} onTabChange={handleTabChange} />
            <div className="dashboard-main">
                <Header userName={dadosDashboard.dadosSupervisor.nome} />
                <StatsCard dashboardData={dadosDashboard} />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosFuncionario={dadosDashboard.dadosSupervisor} onEditProfile={() => setShowProfileModal(true)} />}
                    {activeTab === 'estagiarios' && <AbaEstagiarios estagiarios={dadosDashboard.estagiarios} aoExcluirEstagiario={handleExcluirEstagiario} aoAvaliarEstagiario={abrirModalAvaliacao} aoGerenciarCompetencias={abrirModalCompetencias} aoEditarDadosEstagio={abrirModalDadosEstagio} />}
                    
                    {activeTab === 'avaliacoes' && 
                        <AbaAvaliacoes 
                            estagiarios={dadosDashboard.estagiarios} 
                            aoEditarAvaliacao={abrirModalEdicaoAvaliacao}
                            aoExcluirAvaliacao={handleExcluirAvaliacao}
                        />
                    }
                    
                    {activeTab === 'relatorios' && <AbaRelatorios estagiarios={dadosDashboard.estagiarios} />}
                </div>
            </div>
            
            {showProfileModal && <ModalPerfil onClose={() => setShowProfileModal(false)} dadosIniciais={dadosDashboard.dadosSupervisor} aoAtualizarSucesso={carregarDados} />}
            
            {modalAvaliacaoAberto && (
                <FormularioAvaliacao 
                    estagiario={estagiarioParaAvaliar} 
                    initialData={avaliacaoEmEdicao} 
                    onSubmit={salvarAvaliacao} 
                    onCancel={() => setModalAvaliacaoAberto(false)} 
                    submetendo={submetendoAvaliacao} 
                />
            )}
            
            {modalCompetenciasAberto && <ModalCompetencias estagiario={estagiarioParaCompetencias} onClose={() => setModalCompetenciasAberto(false)} />}
            {modalDadosAberto && <ModalDadosEstagio estagiario={estagiarioParaDados} onClose={() => setModalDadosAberto(false)} onUpdateSuccess={carregarDados} />}
        </div>
    );
}

export default DashboardFuncionario;