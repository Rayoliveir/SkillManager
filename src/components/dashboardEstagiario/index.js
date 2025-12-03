import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import './styles.css'; // Usando o CSS específico que você mandou

import * as api from '../../services/api';
import { 
    User, Rocket, MessageCircle, Trophy, Edit, Star, 
    Briefcase, Medal, Award, Hourglass, MessageSquare, 
    Heart, Zap, TrendingUp, Activity, Crown, Layers, UserCheck, Hand 
} from 'lucide-react';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';

// Mapeamento de Ícones (String do Back-end -> Componente Lucide)
const iconMap = {
    "Hand": Hand,
    "UserCheck": UserCheck,
    "Briefcase": Briefcase,
    "Medal": Medal,
    "Award": Award,
    "Hourglass": Hourglass,
    "MessageSquare": MessageSquare,
    "Heart": Heart,
    "Zap": Zap,
    "TrendingUp": TrendingUp,
    "Activity": Activity,
    "Crown": Crown,
    "Layers": Layers,
    "Star": Star
};

// Função auxiliar para formatar a data
const formatarDataBrasileira = (dataString) => {
    if (!dataString) return '';
    try {
        const [ano, mes, dia] = dataString.split('-');
        return `${dia}/${mes}/${ano}`;
    } catch (e) {
        return dataString;
    }
};

const Sidebar = ({ activeTab, onTabChange }) => (
    <div className="sidebar">
        <div className="sidebar-header">
            <h2>Dashboard</h2>
        </div>
        <nav className="sidebar-nav">
            <button className={`sidebar-item ${activeTab === 'informacoes' ? 'active' : ''}`} onClick={() => onTabChange('informacoes')}>
                <User className="sidebar-icon" size={20} />
                <span className="sidebar-text">Informações</span>
            </button>
            <button className={`sidebar-item ${activeTab === 'competencias' ? 'active' : ''}`} onClick={() => onTabChange('competencias')}>
                <Rocket className="sidebar-icon" size={20} />
                <span className="sidebar-text">Competências</span>
            </button>
            <button className={`sidebar-item ${activeTab === 'feedbacks' ? 'active' : ''}`} onClick={() => onTabChange('feedbacks')}>
                <MessageCircle className="sidebar-icon" size={20} />
                <span className="sidebar-text">Feedbacks</span>
            </button>
            <button className={`sidebar-item ${activeTab === 'conquistas' ? 'active' : ''}`} onClick={() => onTabChange('conquistas')}>
                <Trophy className="sidebar-icon" size={20} />
                <span className="sidebar-text">Conquistas</span>
            </button>
        </nav>
    </div>
);

const Header = ({ userName }) => (
    <header className="dashboard-header-component">
        <div className="header-left">
            <h1>Bem-vindo, <span className="highlight">{userName}</span></h1>
            <p>Aqui está o resumo do seu progresso</p>
        </div>
    </header>
);

const ProfileModal = ({ onClose, dadosIniciais, onUpdateSuccess }) => {
    const [formData, setFormData] = useState({
        nome: dadosIniciais.nome || '',
        email: dadosIniciais.email || '',
        telefone: dadosIniciais.telefone || '',
        senha: '',
        endereco: {
            cep: dadosIniciais.endereco?.cep || '',
            logradouro: dadosIniciais.endereco?.logradouro || '',
            numero: dadosIniciais.endereco?.numero || '',
            bairro: dadosIniciais.endereco?.bairro || '',
            cidade: dadosIniciais.endereco?.cidade || '',
            estados: dadosIniciais.endereco?.estados || 'SP'
        }
    });

    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();
    const estadosBrasileiros = ["AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"];

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (['cep', 'logradouro', 'numero', 'bairro', 'cidade', 'estados'].includes(name)) {
            setFormData(prev => ({ ...prev, endereco: { ...prev.endereco, [name]: value } }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.atualizarEstagiario(dadosIniciais.id, {
                // --- CORREÇÃO AQUI: Envia codigoEmpresa e remove empresaId ---
                codigoEmpresa: dadosIniciais.empresa?.codigoEmpresa, 
                // -------------------------------------------------------------
                dataNascimento: dadosIniciais.dataNascimento,
                genero: dadosIniciais.genero,
                cpf: dadosIniciais.cpf,
                dadosAcademicos: dadosIniciais.dadosAcademicos,
                ...formData
            });
            exibirMensagem('Perfil atualizado com sucesso!', 'sucesso');
            setTimeout(() => { onUpdateSuccess(); onClose(); }, 1500);
        } catch (error) { 
            console.error(error);
            exibirMensagem(error.message || 'Erro ao atualizar.', 'erro'); 
        }
    };
    
    return (
        // ... (O JSX do return continua exatamente igual, não precisa mexer)
        <div className="evaluation-form-overlay">
            <MensagemFeedback mensagem={mensagem} tipo={tipoMensagem} visivel={visivel} onClose={fecharMensagem} />
            <div className="evaluation-form-modal">
                <div className="form-header"><h3>Editar perfil</h3><button className="close-btn" onClick={onClose}>×</button></div>
                <form onSubmit={handleSubmit} className="form-body">
                    <h4 style={{marginBottom:'10px', color:'#161F32', borderBottom:'1px solid #FEEFCF'}}>Dados Pessoais</h4>
                    <div className="form-group"><label>Nome:</label><input className="form-input" name="nome" value={formData.nome} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Email:</label><input className="form-input" name="email" value={formData.email} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Telefone:</label><input className="form-input" name="telefone" value={formData.telefone} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Nova senha:</label><input className="form-input" type="password" name="senha" value={formData.senha} onChange={handleChange} placeholder="*******" /></div>
                    <h4 style={{marginTop:'20px', marginBottom:'10px', color:'#161F32', borderBottom:'1px solid #FEEFCF'}}>Endereço</h4>
                    <div className="form-row" style={{display:'flex', gap:'10px'}}>
                        <div className="form-group" style={{flex:1}}><label>CEP:</label><input className="form-input" name="cep" value={formData.endereco.cep} onChange={handleChange} maxLength="8" required /></div>
                        <div className="form-group" style={{flex:1}}><label>Número:</label><input className="form-input" name="numero" value={formData.endereco.numero} onChange={handleChange} required /></div>
                    </div>
                    <div className="form-group"><label>Logradouro:</label><input className="form-input" name="logradouro" value={formData.endereco.logradouro} onChange={handleChange} required /></div>
                    <div className="form-group"><label>Bairro:</label><input className="form-input" name="bairro" value={formData.endereco.bairro} onChange={handleChange} required /></div>
                    <div className="form-row" style={{display:'flex', gap:'10px'}}>
                        <div className="form-group" style={{flex:2}}><label>Cidade:</label><input className="form-input" name="cidade" value={formData.endereco.cidade} onChange={handleChange} required /></div>
                        <div className="form-group" style={{flex:1}}><label>UF:</label><select className="form-input" name="estados" value={formData.endereco.estados} onChange={handleChange} required>{estadosBrasileiros.map(uf=><option key={uf} value={uf}>{uf}</option>)}</select></div>
                    </div>
                    <div className="form-actions"><button type="button" className="btn-secondary" onClick={onClose}>Cancelar</button><button type="submit" className="btn-primary">Salvar alterações</button></div>
                </form>
            </div>
        </div>
    );
};

const StatsCard = ({ dashboardData }) => {
    const calcularMedia = () => {
        const avaliacoes = dashboardData?.avaliacoes || [];
        if (avaliacoes.length === 0) return 0;
        const soma = avaliacoes.reduce((acc, av) => acc + (av.notaDesempenho + av.notaHabilidadesTecnicas + av.notaHabilidadesComportamentais)/3, 0);
        return (soma / avaliacoes.length).toFixed(1);
    };

    return (
        <div className="stats-overview">
            <div className="stat-card"><div className="stat-icon primary"><Rocket size={24} /></div><div className="stat-info"><h3>{dashboardData?.competencias?.length || 0}</h3><p>Competências</p></div></div>
            <div className="stat-card"><div className="stat-icon secondary"><MessageCircle size={24} /></div><div className="stat-info"><h3>{dashboardData?.avaliacoes?.length || 0}</h3><p>Feedbacks</p></div></div>
            <div className="stat-card"><div className="stat-icon success"><Trophy size={24} /></div><div className="stat-info"><h3>{dashboardData?.conquistas?.length || 0}</h3><p>Conquistas</p></div></div>
            <div className="stat-card"><div className="stat-icon warning"><Star size={24} /></div><div className="stat-info"><h3>{calcularMedia()}</h3><p>Média geral</p></div></div>
        </div>
    );
};

const AbaInformacoes = ({ dadosEstagiario, onEditProfile }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header"><h3>Informações pessoais</h3><button className="btn-primary" onClick={onEditProfile}><Edit className="btn-icon" size={16} /><span className="btn-text">Editar perfil</span></button></div>
                <div className="card-body">
                    <div className="info-details">
                        <div className="info-row"><span className="info-label">Nome:</span><span className="info-value">{dadosEstagiario.nome}</span></div>
                        <div className="info-row"><span className="info-label">Data de nascimento:</span><span className="info-value">{formatarDataBrasileira(dadosEstagiario.dataNascimento)}</span></div>
                        <div className="info-row"><span className="info-label">Gênero:</span><span className="info-value">{dadosEstagiario.genero}</span></div>
                        <div className="info-row"><span className="info-label">Email:</span><span className="info-value">{dadosEstagiario.email}</span></div>
                        <div className="info-row"><span className="info-label">Telefone:</span><span className="info-value">{dadosEstagiario.telefone}</span></div>
                        <div className="info-row"><span className="info-label">CPF:</span><span className="info-value">{dadosEstagiario.cpf}</span></div>
                        <div className="info-row"><span className="info-label">Faculdade:</span><span className="info-value">{dadosEstagiario.dadosAcademicos?.faculdade?.nome}</span></div>
                        <div className="info-row"><span className="info-label">Curso:</span><span className="info-value">{dadosEstagiario.dadosAcademicos?.curso}</span></div>
                        <div className="info-row"><span className="info-label">Semestre:</span><span className="info-value">{dadosEstagiario.dadosAcademicos?.periodoSemestre}</span></div>
                        <div className="info-row"><span className="info-label">Endereço:</span><span className="info-value">{dadosEstagiario.endereco?.logradouro}, {dadosEstagiario.endereco?.numero} - {dadosEstagiario.endereco?.bairro}</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaCompetencias = ({ competencias }) => {
    const getNivelPercent = (nivel) => ({ 'INICIANTE': 20, 'BASICO': 40, 'INTERMEDIARIO': 60, 'AVANCADO': 80, 'ESPECIALISTA': 100 }[nivel] || 10);
    const getNivelLabel = (nivel) => ({ 'INICIANTE': 'Iniciante', 'BASICO': 'Básico', 'INTERMEDIARIO': 'Intermediário', 'AVANCADO': 'Avançado', 'ESPECIALISTA': 'Especialista' }[nivel] || nivel);

    if (competencias && competencias.length > 0) {
        return (
            <div className="tab-content">
                <div className="card">
                    <div className="card-header"><h3>Minhas competências</h3><p>Habilidades reconhecidas</p></div>
                    <div className="card-body">
                        <div className="skills-list">
                            {competencias.map((comp, index) => (
                                <div className="skill-item" key={index}>
                                    <div className="skill-info"><span className="skill-name">{comp.nome}</span><span className="skill-level">{getNivelLabel(comp.nivel)}</span></div>
                                    <div className="skill-bar"><div className="skill-progress" style={{width: `${getNivelPercent(comp.nivel)}%`}}></div></div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    return <div className="tab-content"><div className="card"><div className="card-header"><h3>Minhas competências</h3></div><div className="card-body"><p>Nenhuma competência registrada ainda.</p></div></div></div>;
};

const AbaFeedbacks = ({ feedbacks }) => {
    const renderStars = (rating) => {
        const media = (rating.notaDesempenho + rating.notaHabilidadesTecnicas + rating.notaHabilidadesComportamentais) / 3;
        return Array(5).fill(0).map((_, i) => (<span key={i} className={`star ${i < Math.round(media) ? 'filled' : ''}`}>★</span>));
    };
    
    return (
        <div className="tab-content">
            <div className="card">
                <div className="card-header"><h3>Meus feedbacks</h3><p>Avaliações recebidas</p></div>
                <div className="card-body">
                    <div className="feedbacks-list">
                        {feedbacks && feedbacks.length > 0 ? (
                            feedbacks.map(feedback => (
                                <div key={feedback.id} className="feedback-card">
                                    <div className="feedback-header">
                                        <div className="feedback-author-info"><h4>{feedback.supervisor?.nome || 'Supervisor'}</h4><p className="feedback-role">{feedback.supervisor?.cargo || 'Cargo'} • {feedback.supervisor?.empresa?.nome}</p></div>
                                        <div className="feedback-meta"><span className="feedback-date">{formatarDataBrasileira(feedback.dataAvaliacao)}</span><div className="feedback-rating">{renderStars(feedback)}</div></div>
                                    </div>
                                    <p className="feedback-text">{feedback.feedback}</p>
                                </div>
                            ))
                        ) : (<p>Nenhum feedback recebido ainda.</p>)}
                    </div>
                </div>
            </div>
        </div>
    );
};

// --- NOVA ABA DE CONQUISTAS (ESTILIZADA) ---
const AbaConquistas = ({ conquistas }) => {
    if (conquistas && conquistas.length > 0) {
        return (
            <div className="tab-content">
                <div className="card">
                    <div className="card-header"><h3>Minhas conquistas</h3><p>Marcos alcançados na sua jornada</p></div>
                    <div className="card-body">
                        <div className="achievements-grid">
                            {conquistas.map((conquista, index) => {
                                const IconComponent = iconMap[conquista.icone] || Trophy;
                                return (
                                    <div className="achievement-card" key={index}>
                                        <div className="achievement-icon-wrapper">
                                            <IconComponent size={32} strokeWidth={1.5} />
                                        </div>
                                        <div className="achievement-content">
                                            <h4>{conquista.nome}</h4>
                                            <p>{conquista.descricao}</p>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    return <div className="tab-content"><div className="card"><div className="card-header"><h3>Minhas conquistas</h3></div><div className="card-body"><p>Nenhuma conquista registrada ainda.</p></div></div></div>;
};

function DashboardEstagiario({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [showProfileModal, setShowProfileModal] = useState(false);
    const [dashboardData, setDashboardData] = useState(null); 
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [activeTab, setActiveTab] = useState(initialTab);

    const handleTabChange = (newTab) => { setActiveTab(newTab); const searchParams = new URLSearchParams(location.search); searchParams.set('tab', newTab); navigate({ search: searchParams.toString() }); };
    const handleEditProfile = () => { setShowProfileModal(true); };
    const closeModal = () => { setShowProfileModal(false); };

    const carregarDados = async () => {
        if (!user) return;
        try { setLoading(true); setError(''); const data = await api.getDashboardData(); setDashboardData(data.dashboardEstagiario); } catch (err) { setError(err.message || 'Falha ao carregar dashboard.'); } finally { setLoading(false); }
    };

    useEffect(() => { carregarDados(); }, [user]);

    if (loading) return <div className="dashboard-layout"><div className="loading">Carregando...</div></div>;
    if (error) return <div className="dashboard-layout"><div className="error">{error}</div></div>;
    if (!dashboardData || !dashboardData.dadosEstagiario) return <div className="dashboard-layout"><div className="no-data">Nenhum dado encontrado.</div></div>;

    return (
        <div className="dashboard-layout">
            <Sidebar activeTab={activeTab} onTabChange={handleTabChange} />
            <div className="dashboard-main">
                <Header userName={dashboardData.dadosEstagiario.nome} />
                <StatsCard dashboardData={dashboardData} />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosEstagiario={dashboardData.dadosEstagiario} onEditProfile={handleEditProfile} />}
                    {activeTab === 'competencias' && <AbaCompetencias competencias={dashboardData.competencias} />}
                    {activeTab === 'feedbacks' && <AbaFeedbacks feedbacks={dashboardData.avaliacoes} />}
                    {activeTab === 'conquistas' && <AbaConquistas conquistas={dashboardData.conquistas} />}
                </div>
            </div>
            {showProfileModal && <ProfileModal onClose={closeModal} dadosIniciais={dashboardData.dadosEstagiario} onUpdateSuccess={carregarDados} />}
        </div>
    );
}

export default DashboardEstagiario;