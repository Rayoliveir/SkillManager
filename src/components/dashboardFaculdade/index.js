import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
// import * as api from '../../services/api'; // Commented out as it's not being used currently
import './styles.css';

const TabsNavegacao = ({ abaAtiva, onTabChange }) => (
    <nav className="tabs-nav-container">
        <button className={`tab-button ${abaAtiva === 'informacoes' ? 'active' : ''}`} onClick={() => onTabChange('informacoes')}>
            <span className="tab-icon">🏢</span>
            <span className="tab-text">Informações</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'estagiarios' ? 'active' : ''}`} onClick={() => onTabChange('estagiarios')}>
            <span className="tab-icon">👥</span>
            <span className="tab-text">Estagiários</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'avaliacoes' ? 'active' : ''}`} onClick={() => onTabChange('avaliacoes')}>
            <span className="tab-icon">⭐</span>
            <span className="tab-text">Avaliações</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'relatorios' ? 'active' : ''}`} onClick={() => onTabChange('relatorios')}>
            <span className="tab-icon">📊</span>
            <span className="tab-text">Relatórios</span>
        </button>
    </nav>
);

const AbaInformacoes = ({ dadosFaculdade }) => (
    <div className="dashboard-grid">
        <div className="info-card card">
            <div className="card-header">
                <h3>Informações da Instituição</h3>
                <p>Dados cadastrais da faculdade</p>
            </div>
            <div className="card-content">
                <div className="info-grid">
                    <div className="info-field">
                        <span className="info-label">Nome:</span>
                        <span className="info-value">{dadosFaculdade.nome}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">CNPJ:</span>
                        <span className="info-value">{dadosFaculdade.cnpj}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Telefone:</span>
                        <span className="info-value">{dadosFaculdade.telefone}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Email:</span>
                        <span className="info-value">{dadosFaculdade.email}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Site:</span>
                        <span className="info-value">{dadosFaculdade.site || 'Não informado'}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Endereço:</span>
                        <span className="info-value">
                            {dadosFaculdade.endereco.logradouro}, {dadosFaculdade.endereco.numero}
                            <br />
                            {dadosFaculdade.endereco.bairro}, {dadosFaculdade.endereco.cidade} - {dadosFaculdade.endereco.estados}
                            <br />
                            CEP: {dadosFaculdade.endereco.cep}
                        </span>
                    </div>
                </div>
            </div>
        </div>
        
        <div className="stats-card card">
            <div className="stat-item">
                <div className="stat-icon">👥</div>
                <div className="stat-value"> </div> {/* tem que puxar o valor do backend*/}
                <div className="stat-label">Estagiários</div>
            </div>
            <div className="stat-item">
                <div className="stat-icon">🏢</div>
                <div className="stat-value"> </div> {/* tem que puxar o valor do backend*/}
                <div className="stat-label">Empresas</div>
            </div>
            <div className="stat-item">
                <div className="stat-icon">⭐</div>
                <div className="stat-value"> </div> {/* tem que puxar o valor do backend*/}
                <div className="stat-label">Avaliações</div>
            </div>
        </div>
    </div>
);

const AbaEstagiarios = () => (
    <div className="dashboard-grid">
        <div className="students-card card">
            <div className="card-header">
                <h3>Estagiários Cadastrados</h3>
                <p>Lista de estagiários vinculados à instituição</p>
            </div>
            <div className="card-content">
                <div className="students-table">
                    <div className="table-header">
                        <div className="table-cell">Nome</div>
                        <div className="table-cell">Curso</div>
                        <div className="table-cell">Período</div>
                        <div className="table-cell">Empresa</div>
                        <div className="table-cell">Status</div>
                    </div>
                    <div className="table-row">
                        <div className="table-cell">Ana Silva</div> {/* tem que puxar o valor do backend*/}
                        <div className="table-cell">Ciência da Computação</div> {/* tem que puxar o valor do backend*/}
                        <div className="table-cell">6º Semestre</div> {/* tem que puxar o valor do backend*/}
                        <div className="table-cell">Tech Solutions</div> {/* tem que puxar o valor do backend*/}
                        <div className="table-cell"><span className="status-active">Ativo</span></div> {/* tem que puxar o valor do backend*/}
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaAvaliacoes = () => {
    const [selectedIntern, setSelectedIntern] = useState(null);
    
    // Mock data for interns
    const interns = [
        { id: 1, name: 'Ana Silva', course: 'Ciência da Computação' } // tem que puxar o valor do backend
     
    ];
    
    // Mock data for evaluations
    const evaluations = {
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
        ] // tem que puxar o valor do backend
    };
    
    const renderStars = (rating) => {
        return Array(5).fill(0).map((_, i) => (
            <span key={i} className={`rating-star ${i < rating ? 'filled' : ''}`}>★</span>
        ));
    };
    
    return (
        <div className="dashboard-grid">
            <div className="evaluations-card card">
                <div className="card-header">
                    <h3>Avaliações dos Estagiários</h3>
                    <p>Selecione um estagiário para visualizar suas avaliações</p>
                </div>
                <div className="card-content">
                    {/* Intern selection dropdown */}
                    <div className="intern-selection">
                        <label>Selecione um estagiário:</label>
                        <select 
                            value={selectedIntern || ''} 
                            onChange={(e) => setSelectedIntern(e.target.value ? parseInt(e.target.value) : null)}
                            className="intern-select"
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
                        <div className="evaluations-list">
                            <h4>Avaliações para {interns.find(i => i.id === parseInt(selectedIntern))?.name}:</h4>
                            {evaluations[selectedIntern]?.length > 0 ? (
                                evaluations[selectedIntern].map(evaluation => (
                                    <div key={evaluation.id} className="evaluation-item card">
                                        <div className="evaluation-header">
                                            <span className="evaluation-date">{evaluation.date}</span>
                                        </div>
                                        <p className="evaluation-text">{evaluation.text}</p>
                                        <div className="evaluation-rating">
                                            {renderStars(evaluation.rating)}
                                        </div>
                                        <div className="evaluation-company">{evaluation.company}</div>
                                    </div>
                                ))
                            ) : (
                                <p className="no-evaluations">Nenhuma avaliação encontrada para este estagiário.</p>
                            )}
                        </div>
                    ) : (
                        <div className="evaluations-placeholder">
                            <p>Selecione um estagiário para visualizar suas avaliações.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};
// {/* tem que puxar o valor do backend*/}
const AbaRelatorios = () => (
    <div className="dashboard-grid">
        <div className="reports-card card">
            <div className="card-header">
                <h3>Relatórios e Estatísticas</h3>
                <p>Análise de desempenho dos estagiários</p>
            </div>
            <div className="card-content">
                <div className="reports-grid">
                    <div className="report-item card">
                        <div className="report-icon">📊</div>
                        <div className="report-info">
                            <h4>Desempenho Geral</h4>
                            <p>Média de avaliações: 4.2/5.0</p> 
                        </div>
                    </div>
                    <div className="report-item card">
                        <div className="report-icon">📈</div>
                        <div className="report-info">
                            <h4>Evolução por Curso</h4>
                            <p>Comparativo entre cursos</p>
                        </div>
                    </div>
                    <div className="report-item card">
                        <div className="report-icon">🏢</div>
                        <div className="report-info">
                            <h4>Empresas Parceiras</h4>
                            <p>Ranking de empresas</p>
                        </div>
                    </div>
                    <div className="report-item card">
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
    const [abaAtiva, setAbaAtiva] = useState(initialTab);

    // Update URL when tab changes
    const handleTabChange = (newTab) => {
        setAbaAtiva(newTab);
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

    if (loading) return <main className="dashboard-main-content"><div className="loading">Carregando...</div></main>;
    if (error) return <main className="dashboard-main-content"><div className="error">{error}</div></main>;
    if (!dashboardData) return <main className="dashboard-main-content"><div className="no-data">Nenhum dado encontrado.</div></main>;

    return (
        <main className="dashboard-main-content">
            <div className="dashboard-header">
                <div className="welcome-section">
                    <h1 className="dashboard-title">Bem-vindo, <span className="highlight">{dashboardData.dadosFaculdade.nome}</span></h1>
                    <p className="dashboard-subtitle">Aqui está o resumo das atividades da sua instituição</p>
                </div>
                <div className="user-actions">
                    <button className="btn-primary">
                        <span className="btn-icon">✏️</span>
                        <span className="btn-text">Editar Perfil</span>
                    </button>
                </div>
            </div>
            
            <TabsNavegacao abaAtiva={abaAtiva} onTabChange={handleTabChange} />
            
            <div className="conteudo-aba-container">
                {abaAtiva === 'informacoes' && <AbaInformacoes dadosFaculdade={dashboardData.dadosFaculdade} />}
                {abaAtiva === 'estagiarios' && <AbaEstagiarios />}
                {abaAtiva === 'avaliacoes' && <AbaAvaliacoes />}
                {abaAtiva === 'relatorios' && <AbaRelatorios />}
            </div>
        </main>
    );
}

export default DashboardFaculdade;