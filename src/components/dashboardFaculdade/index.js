import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
// import * as api from '../../services/api'; // Commented out as it's not being used currently
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
                <span className="sidebar-icon">🏢</span>
                <span className="sidebar-text">Informações</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'estagiarios' ? 'active' : ''}`} 
                onClick={() => onTabChange('estagiarios')}
            >
                <span className="sidebar-icon">👥</span>
                <span className="sidebar-text">Estagiários</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'avaliacoes' ? 'active' : ''}`} 
                onClick={() => onTabChange('avaliacoes')}
            >
                <span className="sidebar-icon">⭐</span>
                <span className="sidebar-text">Avaliações</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'relatorios' ? 'active' : ''}`} 
                onClick={() => onTabChange('relatorios')}
            >
                <span className="sidebar-icon">📊</span>
                <span className="sidebar-text">Relatórios</span>
            </button>
        </nav>
    </div>
);

const Header = ({ institutionName }) => (
    <header className="dashboard-header-component">
        <div className="header-left">
            <h1>Bem-vindo, <span className="highlight">{institutionName}</span></h1>
            <p>Aqui está o resumo das atividades da sua instituição</p>
        </div>
        <div className="header-right">
            <button className="btn-primary">
                <span className="btn-icon">✏️</span>
                <span className="btn-text">Editar Perfil</span>
            </button>
        </div>
    </header>
);

const StatsCard = () => (
    <div className="stats-overview">
        <div className="stat-card">
            <div className="stat-icon primary">👥</div>
            <div className="stat-info">
                <h3>120</h3>
                <p>Estagiários</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon secondary">🏢</div>
            <div className="stat-info">
                <h3>25</h3>
                <p>Empresas</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon success">⭐</div>
            <div className="stat-info">
                <h3>85</h3>
                <p>Avaliações</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon warning">📈</div>
            <div className="stat-info">
                <h3>4.2</h3>
                <p>Média Geral</p>
            </div>
        </div>
    </div>
);

const AbaInformacoes = ({ dadosFaculdade }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <h3>Informações da Instituição</h3>
                <div className="info-details">
                    <div className="info-row">
                        <span className="info-label">Nome:</span>
                        <span className="info-value">{dadosFaculdade.nome}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">CNPJ:</span>
                        <span className="info-value">{dadosFaculdade.cnpj}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Telefone:</span>
                        <span className="info-value">{dadosFaculdade.telefone}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Email:</span>
                        <span className="info-value">{dadosFaculdade.email}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Site:</span>
                        <span className="info-value">{dadosFaculdade.site || 'Não informado'}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Endereço:</span>
                        <span className="info-value">
                            {dadosFaculdade.endereco?.logradouro}, {dadosFaculdade.endereco?.numero}
                            <br />
                            {dadosFaculdade.endereco?.bairro}, {dadosFaculdade.endereco?.cidade} - {dadosFaculdade.endereco?.estados}
                            <br />
                            CEP: {dadosFaculdade.endereco?.cep}
                        </span>
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
                <h3>Estagiários Cadastrados</h3>
                <p>Lista de estagiários vinculados à instituição</p>
            </div>
            <div className="card-body">
                <div className="table-container">
                    <table className="data-table">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Curso</th>
                                <th>Período</th>
                                <th>Empresa</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Ana Silva</td>
                                <td>Ciência da Computação</td>
                                <td>6º Semestre</td>
                                <td>Tech Solutions</td>
                                <td><span className="status-badge active">Ativo</span></td>
                            </tr>
                            <tr>
                                <td>Carlos Oliveira</td>
                                <td>Engenharia de Software</td>
                                <td>4º Semestre</td>
                                <td>Innovatech</td>
                                <td><span className="status-badge pending">Pendente</span></td>
                            </tr>
                            <tr>
                                <td>Mariana Costa</td>
                                <td>Sistemas de Informação</td>
                                <td>8º Semestre</td>
                                <td>DataSystems</td>
                                <td><span className="status-badge completed">Concluído</span></td>
                            </tr>
                            <tr>
                                <td>Pedro Santos</td>
                                <td>Análise e Desenvolvimento</td>
                                <td>2º Semestre</td>
                                <td>WebDev</td>
                                <td><span className="status-badge active">Ativo</span></td>
                            </tr>
                            <tr>
                                <td>Julia Pereira</td>
                                <td>Ciência da Computação</td>
                                <td>5º Semestre</td>
                                <td>Tech Solutions</td>
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
    
    // Mock data for interns
    const interns = [
        { id: 1, name: 'Ana Silva', course: 'Ciência da Computação' },
        { id: 2, name: 'Carlos Oliveira', course: 'Engenharia de Software' },
        { id: 3, name: 'Mariana Costa', course: 'Sistemas de Informação' },
        { id: 4, name: 'Pedro Santos', course: 'Análise e Desenvolvimento' }
    ];
    
    // Mock data for evaluations
    const evaluations = {
        1: [
            {
                id: 1,
                date: '15/05/2023',
                text: 'Excelente desempenho nas atividades de desenvolvimento. Mostra grande capacidade de aprendizado e iniciativa.',
                rating: 5,
                company: 'Tech Solutions',
                supervisor: 'João Silva'
            },
            {
                id: 2,
                date: '10/04/2023',
                text: 'Bom trabalho na documentação do projeto. Mostra comprometimento com as atividades.',
                rating: 4,
                company: 'Tech Solutions',
                supervisor: 'Maria Santos'
            }
        ],
        2: [
            {
                id: 3,
                date: '12/05/2023',
                text: 'Demonstra bom conhecimento técnico, mas precisa melhorar na comunicação com a equipe.',
                rating: 3,
                company: 'Innovatech',
                supervisor: 'Pedro Alves'
            }
        ],
        3: [
            {
                id: 4,
                date: '08/05/2023',
                text: 'Ótimo trabalho em equipe. Contribuiu significativamente para o projeto.',
                rating: 5,
                company: 'DataSystems',
                supervisor: 'Ana Costa'
            }
        ],
        4: [
            {
                id: 5,
                date: '05/05/2023',
                text: 'Está se adaptando bem ao ambiente de trabalho. Mostra interesse em aprender.',
                rating: 4,
                company: 'WebDev',
                supervisor: 'Roberto Lima'
            }
        ]
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
                    <p>Selecione um estagiário para visualizar suas avaliações</p>
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
                                                <div className="evaluation-author">
                                                    <h5>{evaluation.supervisor}</h5>
                                                    <p className="evaluation-company">{evaluation.company}</p>
                                                </div>
                                                <div className="evaluation-meta">
                                                    <span className="evaluation-date">{evaluation.date}</span>
                                                    <div className="evaluation-rating">
                                                        {renderStars(evaluation.rating)}
                                                    </div>
                                                </div>
                                            </div>
                                            <p className="evaluation-text">{evaluation.text}</p>
                                        </div>
                                    ))
                                ) : (
                                    <p className="no-data">Nenhuma avaliação encontrada para este estagiário.</p>
                                )}
                            </div>
                        </div>
                    ) : (
                        <div className="placeholder">
                            <p>Selecione um estagiário para visualizar suas avaliações.</p>
                        </div>
                    )}
                </div>
            </div>
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
                        <div className="report-icon">🏢</div>
                        <div className="report-info">
                            <h4>Empresas Parceiras</h4>
                            <p>Ranking de empresas</p>
                        </div>
                    </div>
                    <div className="report-card">
                        <div className="report-icon">📋</div>
                        <div className="report-info">
                            <h4>Relatório Semestral</h4>
                            <p>Relatório completo do semestre</p>
                        </div>
                    </div>
                </div>
                <div className="report-actions">
                    <button className="btn-primary">
                        <span className="btn-icon">📊</span>
                        <span className="btn-text">Gerar Relatório</span>
                    </button>
                    <button className="btn-secondary">
                        <span className="btn-icon">💾</span>
                        <span className="btn-text">Exportar Dados</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
);

function DashboardFaculdade({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    
    const [dashboardData, setDashboardData] = useState({
        dadosFaculdade: {
            nome: "Universidade Tech",
            cnpj: "12.345.678/0001-90",
            telefone: "(11) 3456-7890",
            email: "contato@unitech.edu.br",
            site: "www.unitech.edu.br",
            endereco: {
                logradouro: "Av. Paulista",
                numero: "1000",
                bairro: "Bela Vista",
                cidade: "São Paulo",
                estados: "SP",
                cep: "01310-100"
            }
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
                        dadosFaculdade: {
                            nome: user.nome || "Universidade Tech",
                            cnpj: "12.345.678/0001-90",
                            telefone: "(11) 3456-7890",
                            email: user.email || "contato@unitech.edu.br",
                            site: "www.unitech.edu.br",
                            endereco: {
                                logradouro: "Av. Paulista",
                                numero: "1000",
                                bairro: "Bela Vista",
                                cidade: "São Paulo",
                                estados: "SP",
                                cep: "01310-100"
                            }
                        }
                    });
                    setLoading(false);
                }, 1000);
                
                // Código original para buscar dados reais:
                /*
                let data;
                if (user.roles.includes('ROLE_FACULDADE')) {
                    data = await api.getFaculdadeDashboard();
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
                <Header institutionName={dashboardData.dadosFaculdade.nome} />
                <StatsCard />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosFaculdade={dashboardData.dadosFaculdade} />}
                    {activeTab === 'estagiarios' && <AbaEstagiarios />}
                    {activeTab === 'avaliacoes' && <AbaAvaliacoes />}
                    {activeTab === 'relatorios' && <AbaRelatorios />}
                </div>
            </div>
        </div>
    );
}

export default DashboardFaculdade;