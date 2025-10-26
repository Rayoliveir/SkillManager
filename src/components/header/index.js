import './styles.css'
import { useNavigate } from "react-router-dom";


function Header() {
    const navigate = useNavigate();

    const irParaHome = () => {
        navigate('/');
    }
    const irParaSobre = () => {
        navigate('/sobre')
    }
    const irParaContato = () => {
        navigate('/contato')
    }
    const irParaLogin = () => {
        navigate('/login')
    }
    const irParaCadastro = () => {
        navigate('/cadastro')
    }

        return (
            <>
                <header>
                    <div className="nome__projeto">
                        <div>
                            <img src='/assets/images/logoSemTexto.png' alt='logo'></img>
                        </div>
                        <div>
                            <h1>SKILLMANAGER </h1>
                            <aside>Acompanhe, Avalie, Evolua</aside>
                        </div>

                    </div>

                    <div className="redes__sociais">
                        <div>
                            <img src="/assets/images/facebook.png" alt="facebook" />
                        </div>
                        <div>
                            <img src="/assets/images/instagram.png" alt="instagram" />
                        </div>
                        <div>
                            <img src="/assets/images/twitter.png" alt="twitter" />
                        </div>
                        <div>
                            <img src="/assets/images/chat.png" alt="fale-conosco" />
                        </div>
                    </div>
                </header>

                <nav className="navegacao">
                    <dl>
                        <button onClick={irParaHome}>
                            <dt>HOME</dt>
                        </button>
                        <button onClick={irParaSobre}>
                            <dt>QUEM SOMOS</dt>
                        </button>
                        <button onClick={irParaContato}>
                            <dt>FALE CONOSCO</dt>
                        </button>
                        <button onClick={irParaLogin}>
                            <dt>LOGIN</dt>
                        </button>
                        <button onClick={irParaCadastro}>
                            <dt>CADASTRO</dt>
                        </button>
                    </dl>
                </nav>
            </>
        );
    }

    export default Header