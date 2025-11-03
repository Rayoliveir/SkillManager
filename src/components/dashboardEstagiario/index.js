import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import './styles.css';


const TabsNavegacao = ({ abaAtiva, onTabChange }) => (
    <nav className="tabs-nav-container">
        <button className={`tab-button ${abaAtiva === 'informacoes' ? 'active' : ''}`} onClick={() => onTabChange('informacoes')}>
            <span className="tab-icon">👤</span>
            <span className="tab-text">Informações</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'competencias' ? 'active' : ''}`} onClick={() => onTabChange('competencias')}>
            <span className="tab-icon">🚀</span>
            <span className="tab-text">Competências</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'feedbacks' ? 'active' : ''}`} onClick={() => onTabChange('feedbacks')}>
            <span className="tab-icon">💬</span>
            <span className="tab-text">Feedbacks</span>
        </button>
        <button className={`tab-button ${abaAtiva === 'conquistas' ? 'active' : ''}`} onClick={() => onTabChange('conquistas')}>
            <span className="tab-icon">🏆</span>
            <span className="tab-text">Conquistas</span>
        </button>
    </nav>
);



const AbaInformacoes = ({ dadosEstagiario }) => (
    <div className="dashboard-grid">
        <div className="info-card card">
            <div className="card-header">
                <h3>Informações Pessoais</h3>
                <p>Seus dados cadastrais</p>
            </div>
            <div className="card-content">
                <div className="info-grid">
                    <div className="info-field">
                        <span className="info-label">Nome:</span>
                        <span className="info-value">{dadosEstagiario.nome}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Data de nascimento:</span>
                        <span className="info-value">{dadosEstagiario.dataNascimento}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Genero:</span>
                        <span className="info-value">{dadosEstagiario.genero}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Email:</span>
                        <span className="info-value">{dadosEstagiario.email}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Telefone:</span>
                        <span className="info-value">{dadosEstagiario.telefone}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">CPF:</span>
                        <span className="info-value">{dadosEstagiario.cpf}</span>
                    </div>
                    <div className="info-field">
                        <span className="info-label">Faculdade:</span>
                        {/* <span className="info-value">{}</span> */}
                    </div>
                    <div className="info-field">
                        <span className="info-label">Curso:</span>
                        {/* <span className="info-value">{}</span> */}
                    </div>
                    <div className="info-field">
                        <span className="info-label">Semestre:</span>
                        {/* <span className="info-value">{}</span> */}
                    </div>
                    <div className="info-field">
                        <span className="info-label">R.A:</span>
                        {/* <span className="info-value">{}</span> */}
                    </div>
                <div className="info-field">
                        <span className="info-label">Endereço:</span>
                        <span className="info-value">
                            {dadosEstagiario.endereco.logradouro}, {dadosEstagiario.endereco.numero}
                            <br />
                            {dadosEstagiario.endereco.bairro}, {dadosEstagiario.endereco.cidade} - {dadosEstagiario.endereco.estados}
                            <br />
                            CEP: {dadosEstagiario.endereco.cep}
                        </span>
                    </div>
                </div>
            </div>
        </div>
        
        <div className="stats-card card">
            <div className="stat-item">
                <div className="stat-icon">🚀</div>
                <div className="stat-value">15</div>
                <div className="stat-label">Competências</div>
            </div>
            <div className="stat-item">
                <div className="stat-icon">💬</div>
                <div className="stat-value">8</div>
                <div className="stat-label">Feedbacks</div>
            </div>
            <div className="stat-item">
                <div className="stat-icon">🏆</div>
                <div className="stat-value">5</div>
                <div className="stat-label">Conquistas</div>
            </div>
        </div>
    </div>
);

const AbaCompetencias = () => (
    <div className="dashboard-grid">
        <div className="skills-card card">
            <div className="card-header">
                <h3>Minhas Competências</h3>
                <p>Veja e gerencie suas habilidades</p>
            </div>
            <div className="card-content">
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
                </div>
            </div>
        </div>
    </div>
);

const AbaFeedbacks = () => (
    <div className="dashboard-grid">
        <div className="feedback-card card">
            <div className="card-header">
                <h3>Meus Feedbacks</h3>
                <p>Veja os feedbacks recebidos</p>
            </div>
            <div className="card-content">
                <div className="feedback-list">
                    <div className="feedback-item">
                        <div className="feedback-header">
                            <span className="feedback-author">Supervisor - João Silva</span>
                            <span className="feedback-date">15/05/2023</span>
                        </div>
                        <p className="feedback-text">Excelente desempenho no último projeto. Continue assim!</p>
                        <div className="feedback-rating">
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star">☆</span>
                        </div>
                    </div>
                    <div className="feedback-item">
                        <div className="feedback-header">
                            <span className="feedback-author">Coordenador - Maria Santos</span>
                            <span className="feedback-date">10/04/2023</span>
                        </div>
                        <p className="feedback-text">Bom trabalho na apresentação. Pode melhorar na organização.</p>
                        <div className="feedback-rating">
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star">☆</span>
                            <span className="rating-star">☆</span>
                        </div>
                    </div>
                    <div className="feedback-item">
                        <div className="feedback-header">
                            <span className="feedback-author">Mentor - Pedro Alves</span>
                            <span className="feedback-date">05/03/2023</span>
                        </div>
                        <p className="feedback-text">Demonstrou grande capacidade de aprendizado e adaptabilidade.</p>
                        <div className="feedback-rating">
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                            <span className="rating-star filled">★</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

const AbaConquistas = () => (
    <div className="dashboard-grid">
        <div className="achievements-card card">
            <div className="card-header">
                <h3>Minhas Conquistas</h3>
                <p>Veja suas conquistas e prêmios</p>
            </div>
            <div className="card-content">
                <div className="achievements-grid">
                    <div className="achievement-item card">
                        <div className="achievement-icon">🏆</div>
                        <div className="achievement-info">
                            <h4>Primeiro Projeto</h4>
                            <p>Concluiu o primeiro projeto com sucesso</p>
                            <span className="achievement-date">15/01/2023</span>
                        </div>
                    </div>
                    <div className="achievement-item card">
                        <div className="achievement-icon">⭐</div>
                        <div className="achievement-info">
                            <h4>Destaque do Mês</h4>
                            <p>Reconhecido como destaque em abril</p>
                            <span className="achievement-date">30/04/2023</span>
                        </div>
                    </div>
                    <div className="achievement-item card">
                        <div className="achievement-icon">🎓</div>
                        <div className="achievement-info">
                            <h4>Curso Completo</h4>
                            <p>Finalizou o curso de liderança</p>
                            <span className="achievement-date">10/03/2023</span>
                        </div>
                    </div>
                    <div className="achievement-item card">
                        <div className="achievement-icon">🚀</div>
                        <div className="achievement-info">
                            <h4>Inovação</h4>
                            <p>Propôs solução inovadora para problema</p>
                            <span className="achievement-date">22/02/2023</span>
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
            email: "joao.silva@empresa.com",
            telefone: "(11) 99999-9999",
            cpf: "123.456.789-00"
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
                        dadosEstagiario: {
                            nome: user.nome || "João da Silva",
                            email: user.email || "joao.silva@empresa.com",
                            telefone: "(11) 99999-9999",
                            cpf: "123.456.789-00"
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

    if (loading) return <main className="dashboard-main-content"><div className="loading">Carregando...</div></main>;
    if (error) return <main className="dashboard-main-content"><div className="error">{error}</div></main>;
    if (!dashboardData) return <main className="dashboard-main-content"><div className="no-data">Nenhum dado encontrado.</div></main>;

    return (
        <main className="dashboard-main-content">
            <div className="dashboard-header">
                <div className="welcome-section">
                    <h1 className="dashboard-title">Bem-vindo, <span className="highlight">{dashboardData.dadosEstagiario.nome.split(' ')[0]}</span></h1>
                    <p className="dashboard-subtitle">Aqui está o resumo do seu progresso</p>
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
                {abaAtiva === 'informacoes' && <AbaInformacoes dadosEstagiario={dashboardData.dadosEstagiario} />}
                {abaAtiva === 'competencias' && <AbaCompetencias />}
                {abaAtiva === 'feedbacks' && <AbaFeedbacks />}
                {abaAtiva === 'conquistas' && <AbaConquistas />}
            </div>
        </main>
    );
}

export default DashboardEstagiario;