import { useNavigate } from 'react-router-dom';
import { Users, GraduationCap, User } from 'lucide-react';
import './styles.css'

function Cadastro() {
    const navigate = useNavigate();

    const irParaEstagiario = () => navigate('/cadastroEstagiario');
    const irParaFaculdade = () => navigate('/cadastroFaculdade');
    const irParaFuncionario = () => navigate('/cadastroFuncionario');

    return (
        <main className="main-cadastro">
            <div className="escolha-cadastro">
                <div className="titulo">
                    <h1>Quem você quer cadastrar?</h1>
                    <p>Escolha o tipo de usuário que deseja registrar no sistema</p>
                </div>

                <div className="cards">
                    <div className="card-escolha">
                        <div>
                            <Users size={24} />
                        </div>
                        <h2>Estagiário</h2>
                        <aside>Cadastro para estudantes em estágio</aside>
                        <br /> <br /><br />
                        <button onClick={irParaEstagiario}>Selecionar</button>
                    </div>
                    <div className="card-escolha">
                        <div>
                            <GraduationCap size={24} />
                        </div>
                        <h2>Coordenador</h2>
                        <aside>Cadastro de coordenador<br /> de estágio da instituição de ensino.</aside>
                        <br /> <br /><br />
                        <button onClick={irParaFaculdade}>Selecionar</button>
                    </div>
                    <div className="card-escolha">
                        <div>
                            <User size={24} />
                        </div>
                        <h2>Supervisor</h2>
                        <aside>Cadastro de colaboradores da empresa</aside>
                        <br /> <br /><br />
                        <button onClick={irParaFuncionario}>Selecionar</button>
                    </div>
                </div>

                {/* Features Section */}
                <div className="features-section">
                      <section className="features-section-header">
                            <h1>Nossos valores</h1>
                            <p className="subtitle">Conheça nossos valores</p>
                        </section>
                    <div className="features-grid">
                        <div className="feature-card">
                            <div className="feature-card-content">
                                <div className="feature-icon-wrapper bg-primary-light">
                                    <Users className="text-primary" size={24} />
                                </div>
                                <h3>Gestão completa</h3>
                                <p>
                                    Controle total sobre estagiários e avaliações
                                </p>
                            </div>
                        </div>

                        <div className="feature-card">
                            <div className="feature-card-content">
                                <div className="feature-icon-wrapper bg-success-light">
                                    <GraduationCap className="text-success" size={24} />
                                </div>
                                <h3>Feedbacks em tempo real</h3>
                                <p>
                                    Acompanhamento contínuo do desempenho
                                </p>
                            </div>
                        </div>

                        <div className="feature-card">
                            <div className="feature-card-content">
                                <div className="feature-icon-wrapper bg-warning-light">
                                    <User className="text-warning" size={24} />
                                </div>
                                <h3>Relatórios detalhados</h3>
                                <p>
                                    Análises e métricas de performance
                                </p>
                            </div>
                        </div>

                        <div className="feature-card">
                            <div className="feature-card-content">
                                <div className="feature-icon-wrapper bg-info-light">
                                    <Users className="text-info" size={24} />
                                </div>
                                <h3>Interface intuitiva</h3>
                                <p>
                                    Fácil de usar e responsivo
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Cadastro;