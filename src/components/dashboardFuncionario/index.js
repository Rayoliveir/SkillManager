import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import { User, Users, Star, BarChart, Edit, X, Save, Phone, Mail, FileText, Check, Award } from 'lucide-react';
// import * as api from '../../services/api'; // Commented out as it's not being used currently
import FormularioAvaliacao from '../formularioAvaliacao';
import './styles.css';

const Sidebar = ({ activeTab, onTabChange }) => (
    <div className="sidebar">
        <div className="sidebar-header">
            <h2>Dashboard</h2>
        </div>
        <nav className="sidebar-nav">
            <button 
                className={`sidebar-item ${activeTab === 'informacoes' ? 'active' : ''}`} 
                onClick={() => onTabChange('informacoes')}
            >
                <User className="sidebar-icon" size={20} />
                <span className="sidebar-text">Informações</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'estagiarios' ? 'active' : ''}`} 
                onClick={() => onTabChange('estagiarios')}
            >
                <Users className="sidebar-icon" size={20} />
                <span className="sidebar-text">Estagiários</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'avaliacoes' ? 'active' : ''}`} 
                onClick={() => onTabChange('avaliacoes')}
            >
                <Star className="sidebar-icon" size={20} />
                <span className="sidebar-text">Avaliações</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'relatorios' ? 'active' : ''}`} 
                onClick={() => onTabChange('relatorios')}
            >
                <BarChart className="sidebar-icon" size={20} />
                <span className="sidebar-text">Relatórios</span>
            </button>
        </nav>
    </div>
);

const Header = ({ userName }) => (
    <header className="dashboard-header-component">
        <div className="header-left">
            <h1>Bem-vindo, <span className="highlight">{userName}</span></h1>
            <p>Aqui está o resumo das suas atividades</p>
        </div>
        <div className="header-right">
            {/* Button moved to Informações tab */}
        </div>
    </header>
);

// Simple profile modal component
const ProfileModal = ({ onClose }) => {
    const { user } = useAuth();
    const [profileData, setProfileData] = useState({
        nome: user?.nome || '',
        email: user?.email || '',
        cargo: user?.cargo || ''
    });
    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfileData(prev => ({
            ...prev,
            [name]: value
        }));
    };
    
    const handleSubmit = (e) => {
        e.preventDefault();
        // In a real application, this would call an API to update the profile
        console.log('Profile updated:', profileData);
        onClose();
    };
    
    return (
        <div className="evaluation-form-overlay">
            <div className="evaluation-form-modal">
                <div className="form-header">
                    <h3>Editar Perfil</h3>
                    <button className="close-btn" onClick={onClose}>
                        <X size={24} />
                    </button>
                </div>
                
                <form onSubmit={handleSubmit} className="form-body">
                    <div className="form-group">
                        <label htmlFor="nome">Nome:</label>
                        <input 
                            type="text" 
                            id="nome" 
                            name="nome" 
                            value={profileData.nome} 
                            onChange={handleChange} 
                            className="form-input"
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input 
                            type="email" 
                            id="email" 
                            name="email" 
                            value={profileData.email} 
                            onChange={handleChange} 
                            className="form-input"
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="cargo">Cargo:</label>
                        <input 
                            type="text" 
                            id="cargo" 
                            name="cargo" 
                            value={profileData.cargo} 
                            onChange={handleChange} 
                            className="form-input"
                        />
                    </div>
                    
                    <div className="form-actions">
                        <button type="button" className="btn-secondary" onClick={onClose}>
                            Cancelar
                        </button>
                        <button type="submit" className="btn-primary">
                            <Save size={16} />
                            <span className="btn-text">Salvar Alterações</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

const StatsCard = () => (
    <div className="stats-overview">
        <div className="stat-card">
            <div className="stat-icon primary">
                <Users size={24} />
            </div>
            <div className="stat-info">
                <h3>12</h3>
                <p>Estagiários</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon secondary">
                <Star size={24} />
            </div>
            <div className="stat-info">
                <h3>24</h3>
                <p>Avaliações</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon success">
                <FileText size={24} />
            </div>
            <div className="stat-info">
                <h3>8</h3>
                <p>Pendentes</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon warning">
                <Check size={24} />
            </div>
            <div className="stat-info">
                <h3>15</h3>
                <p>Concluídos</p>
            </div>
        </div>
    </div>
);

const AbaInformacoes = ({ dadosFuncionario, onEditProfile }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <div className="card-header">
                    <h3>Informações Pessoais</h3>
                    <button className="btn-primary" onClick={onEditProfile}>
                        <Edit className="btn-icon" size={16} />
                        <span className="btn-text">Editar Perfil</span>
                    </button>
                </div>
                <div className="card-body">
                    <div className="info-details">
                        <div className="info-row">
                            <span className="info-label">Nome:</span>
                            <span className="info-value">{dadosFuncionario.nome}</span>
                        </div>
                        <div className="info-row">
                            <span className="info-label">Cargo:</span>
                            <span className="info-value">{dadosFuncionario.cargo}</span>
                        </div>
                        <div className="info-row">
                            <span className="info-label">Email:</span>
                            <span className="info-value">{dadosFuncionario.email}</span>
                        </div>
                        <div className="info-row">
                            <span className="info-label">Empresa:</span>
                            <span className="info-value">{dadosFuncionario.empresa}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaEstagiarios = () => (
    <div className="tab-content">
        <div className="card">
            <div className="card-header">
                <h3>Estagiários Sob sua Supervisão</h3>
                <p>Lista de estagiários que você supervisiona</p>
            </div>
            <div className="card-body">
                <div className="table-container">
                    <table className="data-table">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Curso</th>
                                <th>Período</th>
                                <th>Instituição</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Ana Silva</td>
                                <td>Ciência da Computação</td>
                                <td>6º Semestre</td>
                                <td>Universidade Tech</td>
                                <td><span className="status-badge active">Ativo</span></td>
                            </tr>
                            <tr>
                                <td>Carlos Oliveira</td>
                                <td>Engenharia de Software</td>
                                <td>4º Semestre</td>
                                <td>Universidade Tech</td>
                                <td><span className="status-badge pending">Pendente</span></td>
                            </tr>
                            <tr>
                                <td>Mariana Costa</td>
                                <td>Sistemas de Informação</td>
                                <td>8º Semestre</td>
                                <td>Universidade Tech</td>
                                <td><span className="status-badge completed">Concluído</span></td>
                            </tr>
                            <tr>
                                <td>Pedro Santos</td>
                                <td>Análise e Desenvolvimento</td>
                                <td>2º Semestre</td>
                                <td>Universidade Tech</td>
                                <td><span className="status-badge active">Ativo</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
);

const AbaAvaliacoes = () => {
    const [selectedIntern, setSelectedIntern] = useState(null);
    const [showEvaluationForm, setShowEvaluationForm] = useState(false);
    const [evaluations, setEvaluations] = useState({});
    
    // Mock data for interns
    const interns = [
        { id: 1, name: 'Ana Silva', course: 'Ciência da Computação' },
        { id: 2, name: 'Carlos Oliveira', course: 'Engenharia de Software' },
        { id: 3, name: 'Mariana Costa', course: 'Sistemas de Informação' },
        { id: 4, name: 'Pedro Santos', course: 'Análise e Desenvolvimento' }
    ];
    
    // Mock data for evaluations
    const initialEvaluations = {
        1: [
            {
                id: 1,
                date: '15/05/2023',
                text: 'Excelente desempenho nas atividades de desenvolvimento. Mostra grande capacidade de aprendizado e iniciativa.',
                rating: 5,
                company: 'Tech Solutions'
            },
            {
                id: 2,
                date: '10/04/2023',
                text: 'Bom trabalho na documentação do projeto. Mostra comprometimento com as atividades.',
                rating: 4,
                company: 'Tech Solutions'
            }
        ],
        2: [
            {
                id: 3,
                date: '10/05/2023',
                text: 'Bom trabalho na documentação do projeto. Precisa melhorar na comunicação com a equipe.',
                rating: 3,
                company: 'Tech Solutions'
            }
        ],
        3: [
            {
                id: 4,
                date: '05/05/2023',
                text: 'Demonstra bom conhecimento técnico, mas precisa melhorar na organização das entregas.',
                rating: 4,
                company: 'Tech Solutions'
            },
            {
                id: 5,
                date: '01/04/2023',
                text: 'Ótimo trabalho em equipe. Contribuiu significativamente para o projeto.',
                rating: 5,
                company: 'Tech Solutions'
            }
        ],
        4: [
            {
                id: 6,
                date: '12/05/2023',
                text: 'Está se adaptando bem ao ambiente de trabalho. Mostra interesse em aprender.',
                rating: 4,
                company: 'Tech Solutions'
            }
        ]
    };
    
    useEffect(() => {
        setEvaluations(initialEvaluations);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps
    
    const handleAddEvaluation = (evaluationData) => {
        const newEvaluation = {
            id: Date.now(), // Simple ID generation
            date: evaluationData.data,
            text: evaluationData.descricao,
            rating: evaluationData.classificacao,
            company: 'Tech Solutions'
        };
        
        setEvaluations(prev => ({
            ...prev,
            [evaluationData.estagiarioId]: [
                ...(prev[evaluationData.estagiarioId] || []),
                newEvaluation
            ]
        }));
        
        setShowEvaluationForm(false);
    };
    
    const renderStars = (rating) => {
        return Array(5).fill(0).map((_, i) => (
            <span key={i} className={`star ${i < rating ? 'filled' : ''}`}>★</span>
        ));
    };
    
    return (
        <div className="tab-content">
            <div className="card">
                <div className="card-header">
                    <h3>Avaliações dos Estagiários</h3>
                    <p>Selecione um estagiário para visualizar ou registrar avaliações</p>
                </div>
                <div className="card-body">
                    {/* Intern selection */}
                    <div className="form-group">
                        <label>Selecione um estagiário:</label>
                        <select 
                            value={selectedIntern || ''} 
                            onChange={(e) => setSelectedIntern(e.target.value ? parseInt(e.target.value) : null)}
                            className="form-select"
                        >
                            <option value="">-- Selecione um estagiário --</option>
                            {interns.map(intern => (
                                <option key={intern.id} value={intern.id}>
                                    {intern.name} - {intern.course}
                                </option>
                            ))}
                        </select>
                    </div>
                    
                    {/* Evaluations display */}
                    {selectedIntern ? (
                        <div className="evaluations-section">
                            <h4>Avaliações para {interns.find(i => i.id === parseInt(selectedIntern))?.name}:</h4>
                            <div className="evaluations-list">
                                {evaluations[selectedIntern]?.length > 0 ? (
                                    evaluations[selectedIntern].map(evaluation => (
                                        <div key={evaluation.id} className="evaluation-card">
                                            <div className="evaluation-header">
                                                <span className="evaluation-date">{evaluation.date}</span>
                                                <div className="evaluation-rating">
                                                    {renderStars(evaluation.rating)}
                                                </div>
                                            </div>
                                            <p className="evaluation-text">{evaluation.text}</p>
                                            <div className="evaluation-company">{evaluation.company}</div>
                                        </div>
                                    ))
                                ) : (
                                    <p className="no-data">Nenhuma avaliação encontrada para este estagiário.</p>
                                )}
                            </div>
                            
                            {/* Add new evaluation button */}
                            <div className="form-actions">
                                <button 
                                    type="button" 
                                    className="btn-primary"
                                    onClick={() => setShowEvaluationForm(true)}
                                >
                                    <span className="btn-icon">⭐</span>
                                    <span className="btn-text">Nova Avaliação</span>
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="placeholder">
                            <p>Selecione um estagiário para visualizar ou registrar avaliações.</p>
                        </div>
                    )}
                </div>
            </div>
            
            {/* Evaluation Form Modal */}
            {showEvaluationForm && selectedIntern && (
                <FormularioAvaliacao
                    intern={interns.find(i => i.id === selectedIntern)}
                    onSubmit={handleAddEvaluation}
                    onCancel={() => setShowEvaluationForm(false)}
                />
            )}
        </div>
    );
};

const AbaRelatorios = () => (
    <div className="tab-content">
        <div className="card">
            <div className="card-header">
                <h3>Relatórios e Estatísticas</h3>
                <p>Análise de desempenho dos estagiários</p>
            </div>
            <div className="card-body">
                <div className="reports-grid">
                    <div className="report-card">
                        <div className="report-icon">📊</div>
                        <div className="report-info">
                            <h4>Desempenho Geral</h4>
                            <p>Média de avaliações: 4.2/5.0</p>
                        </div>
                    </div>
                    <div className="report-card">
                        <div className="report-icon">📈</div>
                        <div className="report-info">
                            <h4>Evolução por Curso</h4>
                            <p>Comparativo entre cursos</p>
                        </div>
                    </div>
                    <div className="report-card">
                        <div className="report-icon">📋</div>
                        <div className="report-info">
                            <h4>Relatório Semestral</h4>
                            <p>Relatório completo do semestre</p>
                        </div>
                    </div>
                    <div className="report-card">
                        <div className="report-icon">🏆</div>
                        <div className="report-info">
                            <h4>Destaque do Mês</h4>
                            <p>Estagiários com melhor desempenho</p>
                        </div>
                    </div>
                </div>
                <div className="report-actions">
                    <button className="btn-primary">
                        <span className="btn-icon">📊</span>
                        <span className="btn-text">Gerar Relatório</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
);

function DashboardFuncionario({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [showProfileModal, setShowProfileModal] = useState(false);
    
    const [dashboardData, setDashboardData] = useState({
        dadosFuncionario: {
            nome: "João Silva",
            cargo: "Supervisor de TI",
            email: "joao.silva@techsolutions.com",
            empresa: "Tech Solutions"
        }
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [activeTab, setActiveTab] = useState(initialTab);

    // Update URL when tab changes
    const handleTabChange = (newTab) => {
        setActiveTab(newTab);
        const searchParams = new URLSearchParams(location.search);
        searchParams.set('tab', newTab);
        navigate({ search: searchParams.toString() });
    };
    
    const handleEditProfile = () => {
        setShowProfileModal(true);
    };
    
    const closeModal = () => {
        setShowProfileModal(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            if (!user || !user.roles) { 
                setLoading(false); 
                return; 
            };
            try {
                setLoading(true);
                // Simulando dados do dashboard
                setTimeout(() => {
                    setDashboardData({
                        dadosFuncionario: {
                            nome: user.nome || "João Silva",
                            cargo: "Supervisor de TI",
                            email: user.email || "joao.silva@techsolutions.com",
                            empresa: "Tech Solutions"
                        }
                    });
                    setLoading(false);
                }, 1000);
                
                // Código original para buscar dados reais:
                /*
                let data;
                if (user.roles.includes('ROLE_SUPERVISOR') || user.roles.includes('ROLE_FUNCIONARIO')) {
                    data = await api.getSupervisorDashboard();
                }
                setDashboardData(data);
                */
            } catch (err) {
                setError('Falha ao carregar os dados do dashboard.');
                setLoading(false);
            }
        };
        fetchData();
    }, [user]);

    if (loading) return <div className="dashboard-layout"><div className="loading">Carregando...</div></div>;
    if (error) return <div className="dashboard-layout"><div className="error">{error}</div></div>;
    if (!dashboardData) return <div className="dashboard-layout"><div className="no-data">Nenhum dado encontrado.</div></div>;

    return (
        <div className="dashboard-layout">
            <Sidebar activeTab={activeTab} onTabChange={handleTabChange} />
            <div className="dashboard-main">
                <Header userName={dashboardData.dadosFuncionario.nome} />
                <StatsCard />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosFuncionario={dashboardData.dadosFuncionario} onEditProfile={handleEditProfile} />}
                    {activeTab === 'estagiarios' && <AbaEstagiarios />}
                    {activeTab === 'avaliacoes' && <AbaAvaliacoes />}
                    {activeTab === 'relatorios' && <AbaRelatorios />}
                </div>
            </div>
            
            {showProfileModal && (
                <ProfileModal onClose={closeModal} />
            )}
        </div>
    );
}

export default DashboardFuncionario;