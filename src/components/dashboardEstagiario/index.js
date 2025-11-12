import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
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
                <span className="sidebar-icon">👤</span>
                <span className="sidebar-text">Informações</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'competencias' ? 'active' : ''}`} 
                onClick={() => onTabChange('competencias')}
            >
                <span className="sidebar-icon">🚀</span>
                <span className="sidebar-text">Competências</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'feedbacks' ? 'active' : ''}`} 
                onClick={() => onTabChange('feedbacks')}
            >
                <span className="sidebar-icon">💬</span>
                <span className="sidebar-text">Feedbacks</span>
            </button>
            <button 
                className={`sidebar-item ${activeTab === 'conquistas' ? 'active' : ''}`} 
                onClick={() => onTabChange('conquistas')}
            >
                <span className="sidebar-icon">🏆</span>
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
            <div className="stat-icon primary">🚀</div>
            <div className="stat-info">
                <h3>15</h3>
                <p>Competências</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon secondary">💬</div>
            <div className="stat-info">
                <h3>8</h3>
                <p>Feedbacks</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon success">🏆</div>
            <div className="stat-info">
                <h3>5</h3>
                <p>Conquistas</p>
            </div>
        </div>
        <div className="stat-card">
            <div className="stat-icon warning">⭐</div>
            <div className="stat-info">
                <h3>4.2</h3>
                <p>Média</p>
            </div>
        </div>
    </div>
);

const AbaInformacoes = ({ dadosEstagiario }) => (
    <div className="tab-content">
        <div className="info-grid">
            <div className="info-card">
                <h3>Informações Pessoais</h3>
                <div className="info-details">
                    <div className="info-row">
                        <span className="info-label">Nome:</span>
                        <span className="info-value">{dadosEstagiario.nome}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Data de nascimento:</span>
                        <span className="info-value">{dadosEstagiario.dataNascimento}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Gênero:</span>
                        <span className="info-value">{dadosEstagiario.genero}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Email:</span>
                        <span className="info-value">{dadosEstagiario.email}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Telefone:</span>
                        <span className="info-value">{dadosEstagiario.telefone}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">CPF:</span>
                        <span className="info-value">{dadosEstagiario.cpf}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Faculdade:</span>
                        <span className="info-value">{dadosEstagiario.faculdade}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Curso:</span>
                        <span className="info-value">{dadosEstagiario.curso}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Semestre:</span>
                        <span className="info-value">{dadosEstagiario.semestre}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">R.A:</span>
                        <span className="info-value">{dadosEstagiario.ra}</span>
                    </div>
                    <div className="info-row">
                        <span className="info-label">Endereço:</span>
                        <span className="info-value">
                            {dadosEstagiario.endereco?.logradouro}, {dadosEstagiario.endereco?.numero}
                            <br />
                            {dadosEstagiario.endereco?.bairro}, {dadosEstagiario.endereco?.cidade} - {dadosEstagiario.endereco?.estados}
                            <br />
                            CEP: {dadosEstagiario.endereco?.cep}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaCompetencias = () => (
    <div className="tab-content">
        <div className="card">
            <div className="card-header">
                <h3>Minhas Competências</h3>
                <p>Veja e gerencie suas habilidades</p>
            </div>
            <div className="card-body">
                <div className="skills-list">
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Comunicação</span>
                            <span className="skill-level">Avançado</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '85%'}}></div>
                        </div>
                    </div>
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Trabalho em Equipe</span>
                            <span className="skill-level">Intermediário</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '80%'}}></div>
                        </div>
                    </div>
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Resolução de Problemas</span>
                            <span className="skill-level">Iniciante</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '45%'}}></div>
                        </div>
                    </div>
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Liderança</span>
                            <span className="skill-level">Iniciante</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '30%'}}></div>
                        </div>
                    </div>
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Programação</span>
                            <span className="skill-level">Intermediário</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '65%'}}></div>
                        </div>
                    </div>
                    <div className="skill-item">
                        <div className="skill-info">
                            <span className="skill-name">Design</span>
                            <span className="skill-level">Básico</span>
                        </div>
                        <div className="skill-bar">
                            <div className="skill-progress" style={{width: '25%'}}></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

// Updated AbaFeedbacks to show evaluations from funcionarios
const AbaFeedbacks = () => {
    // Mock data for feedbacks from funcionarios
    const feedbacks = [
        {
            id: 1,
            author: "João Silva",
            role: "Supervisor de TI",
            date: "15/05/2023",
            text: "Excelente desempenho nas atividades de desenvolvimento. Mostra grande capacidade de aprendizado e iniciativa.",
            rating: 5,
            company: "Tech Solutions"
        },
        {
            id: 2,
            author: "Maria Santos",
            role: "Coordenadora",
            date: "10/04/2023",
            text: "Bom trabalho na documentação do projeto. Mostra comprometimento com as atividades.",
            rating: 4,
            company: "Tech Solutions"
        },
        {
            id: 3,
            author: "Pedro Alves",
            role: "Mentor",
            date: "05/03/2023",
            text: "Demonstrou grande capacidade de aprendizado e adaptabilidade. Continua evoluindo bem.",
            rating: 5,
            company: "Tech Solutions"
        },
        {
            id: 4,
            author: "Ana Costa",
            role: "Supervisora",
            date: "01/02/2023",
            text: "Precisa melhorar na organização das entregas, mas o conteúdo está bom.",
            rating: 3,
            company: "Tech Solutions"
        }
    ];
    
    const renderStars = (rating) => {
        return Array(5).fill(0).map((_, i) => (
            <span key={i} className={`star ${i < rating ? 'filled' : ''}`}>★</span>
        ));
    };
    
    return (
        <div className="tab-content">
            <div className="card">
                <div className="card-header">
                    <h3>Meus Feedbacks</h3>
                    <p>Veja os feedbacks recebidos dos supervisores</p>
                </div>
                <div className="card-body">
                    <div className="feedbacks-list">
                        {feedbacks.map(feedback => (
                            <div key={feedback.id} className="feedback-card">
                                <div className="feedback-header">
                                    <div className="feedback-author-info">
                                        <h4>{feedback.author}</h4>
                                        <p className="feedback-role">{feedback.role} • {feedback.company}</p>
                                    </div>
                                    <div className="feedback-meta">
                                        <span className="feedback-date">{feedback.date}</span>
                                        <div className="feedback-rating">
                                            {renderStars(feedback.rating)}
                                        </div>
                                    </div>
                                </div>
                                <p className="feedback-text">{feedback.text}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

const AbaConquistas = () => (
    <div className="tab-content">
        <div className="card">
            <div className="card-header">
                <h3>Minhas Conquistas</h3>
                <p>Veja suas conquistas e prêmios</p>
            </div>
            <div className="card-body">
                <div className="achievements-grid">
                    <div className="achievement-card">
                        <div className="achievement-icon">🏆</div>
                        <div className="achievement-info">
                            <h4>Primeiro Projeto</h4>
                            <p>Concluiu o primeiro projeto com sucesso</p>
                            <span className="achievement-date">15/01/2023</span>
                        </div>
                    </div>
                    <div className="achievement-card">
                        <div className="achievement-icon">⭐</div>
                        <div className="achievement-info">
                            <h4>Destaque do Mês</h4>
                            <p>Reconhecido como destaque em abril</p>
                            <span className="achievement-date">30/04/2023</span>
                        </div>
                    </div>
                    <div className="achievement-card">
                        <div className="achievement-icon">🎓</div>
                        <div className="achievement-info">
                            <h4>Curso Completo</h4>
                            <p>Finalizou o curso de liderança</p>
                            <span className="achievement-date">10/03/2023</span>
                        </div>
                    </div>
                    <div className="achievement-card">
                        <div className="achievement-icon">🚀</div>
                        <div className="achievement-info">
                            <h4>Inovação</h4>
                            <p>Propôs solução inovadora para problema</p>
                            <span className="achievement-date">22/02/2023</span>
                        </div>
                    </div>
                    <div className="achievement-card">
                        <div className="achievement-icon">📚</div>
                        <div className="achievement-info">
                            <h4>100% Presença</h4>
                            <p>Manteve 100% de presença no mês</p>
                            <span className="achievement-date">31/05/2023</span>
                        </div>
                    </div>
                    <div className="achievement-card">
                        <div className="achievement-icon">💡</div>
                        <div className="achievement-info">
                            <h4>Ideia Criativa</h4>
                            <p>Sugestão implementada pela equipe</p>
                            <span className="achievement-date">18/04/2023</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

function DashboardEstagiario({ initialTab = 'informacoes' }) {
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    
    const [dashboardData, setDashboardData] = useState({
        dadosEstagiario: {
            nome: "João da Silva",
            dataNascimento: "15/03/2000",
            genero: "Masculino",
            email: "joao.silva@empresa.com",
            telefone: "(11) 99999-9999",
            cpf: "123.456.789-00",
            faculdade: "Universidade Tech",
            curso: "Ciência da Computação",
            semestre: "6º Semestre",
            ra: "123456",
            endereco: {
                logradouro: "Rua Exemplo",
                numero: "123",
                bairro: "Centro",
                cidade: "São Paulo",
                estados: "SP",
                cep: "12345-678"
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
                        dadosEstagiario: {
                            nome: user.nome || "João da Silva",
                            dataNascimento: "15/03/2000",
                            genero: "Masculino",
                            email: user.email || "joao.silva@empresa.com",
                            telefone: "(11) 99999-9999",
                            cpf: "123.456.789-00",
                            faculdade: "Universidade Tech",
                            curso: "Ciência da Computação",
                            semestre: "6º Semestre",
                            ra: "123456",
                            endereco: {
                                logradouro: "Rua Exemplo",
                                numero: "123",
                                bairro: "Centro",
                                cidade: "São Paulo",
                                estados: "SP",
                                cep: "12345-678"
                            }
                        }
                    });
                    setLoading(false);
                }, 1000);
                
                // Código original para buscar dados reais:
                /*
                let data;
                if (user.roles.includes('ROLE_ESTAGIARIO')) {
                    data = await api.getEstagiarioDashboard();
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
                <Header userName={dashboardData.dadosEstagiario.nome.split(' ')[0]} />
                <StatsCard />
                <div className="tab-content-container">
                    {activeTab === 'informacoes' && <AbaInformacoes dadosEstagiario={dashboardData.dadosEstagiario} />}
                    {activeTab === 'competencias' && <AbaCompetencias />}
                    {activeTab === 'feedbacks' && <AbaFeedbacks />}
                    {activeTab === 'conquistas' && <AbaConquistas />}
                </div>
            </div>
        </div>
    );
}

export default DashboardEstagiario;