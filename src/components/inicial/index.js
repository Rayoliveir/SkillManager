import './styles.css'
import { useNavigate } from 'react-router-dom';

function Inicial() {
  const navigate = useNavigate();

  const handleStartNow = () => {
    navigate('/login');
  };

  return (
    <div>
      <main className="main-inicial">
        <div className="hero-section">
          <div className="hero-content">
            <div className="hero-badge">
              <span className="badge-text">NOVO</span>
              <span className="badge-divider">•</span>
              <span className="badge-highlight">Versão 1.0 disponível</span>
            </div>
            <h1 className="hero-title">Gerencie suas <span className="highlight">habilidades</span> de forma inteligente</h1>
            <p className="hero-description">
              Transforme seu potencial em resultados concretos. Nosso sistema inteligente ajuda você a identificar, 
              desenvolver e acompanhar suas competências de maneira eficaz e personalizada.
            </p>
            <div className="hero-buttons">
              <button className="btn-primary" onClick={handleStartNow}>Começar agora</button>
            </div>
            <div className="hero-stats">
              <div className="stat-item">
                <span className="stat-number">500+</span>
                <span className="stat-label">Empresas</span>
              </div>
              <div className="stat-item">
                <span className="stat-number">10K+</span>
                <span className="stat-label">Usuários</span>
              </div>
              <div className="stat-item">
                <span className="stat-number">98%</span>
                <span className="stat-label">Satisfação</span>
              </div>
            </div>
          </div>
          <div className="hero-image">
            <div className="logo-central">
              <div>
                <div className="logo-icon-container">
                  <img src="/assets/images/icone-grafico.png" alt="icone-grafico" />
                </div>
                <h1>SKILLMANAGER</h1>
                <aside>Evolua suas competências</aside>
              </div>
            </div>
            <div className="hero-decoration">
              <div className="decoration-circle circle-1"></div>
              <div className="decoration-circle circle-2"></div>
              <div className="decoration-circle circle-3"></div>
            </div>
          </div>
        </div>
      </main>

      <div className="features-section">
        <div className="section-header">
          <h2 className="section-title">Recursos que <span className="highlight">impulsionam</span> resultados</h2>
          <p className="section-description">
            Ferramentas completas para transformar a gestão de competências em vantagem competitiva.
          </p>
        </div>

        <div className="features-grid">
          <div className="feature-card">
            <div className="feature-icon primary">
              <img src="../assets/images/icone-pessoa.png" alt="icone-pessoa" />
            </div>
            <div className="feature-content">
              <h3>Gestão de usuários</h3>
              <p>Cadastre e gerencie perfis de usuários com facilidade. Organize equipes e acompanhe progressos individuais.</p>
            </div>
            <div className="feature-link">
              <span>Saiba mais</span>
              <span className="arrow">→</span>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon secondary">
              <img src="../assets/images/icone-grafico.png" alt="icone-grafico" />
            </div>
            <div className="feature-content">
              <h3>Avaliações detalhadas</h3>
              <p>Sistema completo de avaliação de competências com métricas precisas e relatórios visuais.</p>
            </div>
            <div className="feature-link">
              <span>Saiba mais</span>
              <span className="arrow">→</span>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon primary">
              <img src="../assets/images/icone-alvo.png" alt="icone-alvo" />
            </div>
            <div className="feature-content">
              <h3>Metas personalizadas</h3>
              <p>Defina objetivos customizados para cada habilidade e acompanhe o progresso em tempo real.</p>
            </div>
            <div className="feature-link">
              <span>Saiba mais</span>
              <span className="arrow">→</span>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon secondary">
              <img src="../assets/images/icone-prémio.png" alt="icone-prémio" />
            </div>
            <div className="feature-content">
              <h3>Reconhecimento</h3>
              <p>Sistema de badges e certificações para valorizar conquistas e motivar o desenvolvimento contínuo.</p>
            </div>
            <div className="feature-link">
              <span>Saiba mais</span>
              <span className="arrow">→</span>
            </div>
          </div>
        </div>
      </div>

      <div className="testimonial-section">
        <div className="testimonial-container">
          <div className="testimonial-content">
            <h3 className="testimonial-quote">"O SkillManager revolucionou nossa forma de gerenciar o desenvolvimento profissional. A plataforma é intuitiva e os resultados são impressionantes."</h3>
            {/* <div className="testimonial-author">
              <div className="author-avatar">
                <img src="/assets/images/default-avatar.png" alt="Avatar" />
              </div>
              <div className="author-info">
                <h4 className="author-name">Maria Silva</h4>
                <p className="author-title">Diretora de RH, TechCorp</p>
              </div>
            </div> */}
          </div>
          <div className="testimonial-stats">
            <div className="stat-item">
              <span className="stat-number">4.9</span>
              <span className="stat-label">Avaliação média</span>
            </div>
            <div className="stat-item">
              <span className="stat-number">95%</span>
              <span className="stat-label">Recomendariam</span>
            </div>
          </div>
        </div>
      </div>

      <div className="cta-section">
        <div className="cta-content">
          <h2 className="cta-title">Pronto para <span className="highlight">evoluir</span> suas competências?</h2>
          <p className="cta-description">
            Junte-se a centenas de profissionais que já transformaram sua carreira com o SkillManager
          </p>
          <div className="cta-form">
            <input type="email" placeholder="Seu melhor e-mail" className="cta-input" />
            <button className="cta-button" onClick={handleStartNow}>Começar grátis</button>
          </div>
          <div className="cta-guarantee">
            <span className="guarantee-icon">✓</span>
            <span className="guarantee-text">Comece gratuitamente • Cancele a qualquer momento</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Inicial