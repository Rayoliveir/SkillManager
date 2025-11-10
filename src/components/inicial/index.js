import './styles.css'
import Header from '../header';

function Inicial() {
  return (

    <div>

      <main className="main-inicial">

        <div className='descricao'>

          <div className="cabeca">
            <h1>Gerencie suas <span>habilidades</span> de <br /> forma inteligente</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod temporincididunt ut labore et
              dolore magna aliqua. <br /> Ut enim ad minim veniam, quis nostrud <br />exercitation ullamco laboris nisi ut aliquip
              ex ea commodo consequat.
            </p>
          </div>

          <div className="cards-cadastro-avaliacao">
            <div className="cadastro">
              <img src="/assets/images/icone-gerenciar.png" alt="icone-gerenciar" />
              <h3>Cadastros</h3>
              <aside>Gerencie usuários e perfis</aside>
            </div>

            <div className="avaliacao">
              <img src="/assets/images/icone-medalha.png" alt="icone-medalha" />
              <h3>Avaliações</h3>
              <aside>Avalie competências</aside>
            </div>
          </div>

        </div>

        <div className="logo-central">

          <div>

            <div className="logo-icon-container">
              <img src="/assets/images/icone-grafico.png" alt="icone-grafico" />
            </div>

            <h1>SKILLMANAGER</h1>
            <aside>Evolua suas competências</aside>

          </div>

        </div>
      </main>

      <div class="recursos">
        <div class="titulo-recursos">
          <h1>Recursos que <span>impulsionam</span> resultados</h1>
          <p>Ferramentas completas para transformar a gestão de competencias em vantagem competitiva.</p>
        </div>

        <div class="cards">
          <div class="card__descri">
            <div class="card__icon1">
              <img src="../assets/images/icone-pessoa.png" alt="icone-pessoa" />
            </div>

            <div class="card__text">
              <h3>Gestão de usuários</h3>
              <p>Cadastre e gerencie perfils de usuarios com facilidade. Organize equipes e acompanhe progressos individuais.</p>
            </div>
          </div>

          <div class="card__descri">
            <div class="card__icon2">
              <img src="../assets/images/icone-grafico.png" alt="icone-grafico" width={25} />
            </div>

            <div class="card__text">
              <h3>Avaliações detalhadas</h3>
              <p>Sistema completo de avaliação de competencias com metricas precisas e relatorios visuais.</p>
            </div>
          </div>

          <div class="card__descri">
            <div class="card__icon1">
              <img src="../assets/images/icone-alvo.png" alt="icone-alvo" width={25} />
            </div>

            <div class="card__text">
              <h3>Metas personalizadas</h3>
              <p>Defina objetivos customizados para cada habilidade e acompanhe o progresso em tempo real.</p>
            </div>
          </div>

          <div class="card__descri">
            <div class="card__icon2">
              <img src="../assets/images/icone-prémio.png" alt="icone-prémio" width={30} />
            </div>

            <div class="card__text">
              <h3>Reconhecimento</h3>
              <p>Sistema de badges e certificações para valorizar conquitas e motivar o desenvolvimento continuo.</p>
            </div>
          </div>

        </div>

      </div>

      <div class="competencias">
        <div class="titulo-competencias">
          <h1>Pronto para <span>evoluir</span> suas competências?</h1>
          <p>Junte-se a centenas de profissionais que ja transfprmaram sua carreira com o SkillManager</p>
        </div>
        
        <div class="cta-button">
          <input type="text" placeholder="Seu melhor E-mail" />
          <button>Começar grátis</button>
        </div>

      </div>

    </div>

  );
}

export default Inicial