import './styles.css';
import Header from '../../components/header';
import Equipe from '../../components/equipe';
import Footer from '../../components/footer';

function TelaQuemSomos() {
    return (
        <div className="pag-quem-somos-container">
            <Header />
            <main className="quem-somos-content">
                <section className="quem-somos-header">
                    <h1>Quem Somos</h1>
                    <p className="subtitle">Conheça nossa equipe e nossa missão</p>
                </section>
                
                <section className="quem-somos-main">
                    <div className="mission-vision">
                        <div className="mission">
                            <h2>Nossa Missão</h2>
                            <p>Facilitar a gestão de estagiários através de avaliações personalizadas, feedback contínuo e análise automatizada de desempenho, promovendo um ambiente de aprendizado eficaz e produtivo.</p>
                        </div>
                        
                        <div className="vision">
                            <h2>Nossa Visão</h2>
                            <p>Ser a plataforma líder em gerenciamento de estágios, reconhecida por sua inovação, simplicidade e impacto positivo na formação profissional dos estudantes.</p>
                        </div>
                    </div>
                    
                    <div className="values">
                        <h2>Nossos Valores</h2>
                        <div className="values-grid">
                            <div className="value-card">
                                <h3>Inovação</h3>
                                <p>Buscamos constantemente novas soluções para melhorar a experiência de gestão de estagiários.</p>
                            </div>
                            
                            <div className="value-card">
                                <h3>Excelência</h3>
                                <p>Comprometidos com a qualidade em todos os aspectos do nosso trabalho.</p>
                            </div>
                            
                            <div className="value-card">
                                <h3>Colaboração</h3>
                                <p>Acreditamos no poder do trabalho em equipe para alcançar resultados extraordinários.</p>
                            </div>
                            
                            <div className="value-card">
                                <h3>Transparência</h3>
                                <p>Promovemos um ambiente aberto e honesto entre todos os envolvidos.</p>
                            </div>
                        </div>
                    </div>
                </section>
                
                {/* Added Equipe section here */}
                <section className="equipe-section">
                    <Equipe />
                </section>
            </main>
            <Footer />
        </div>
    );
}

export default TelaQuemSomos;