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
    </div>

  );
}

export default Inicial