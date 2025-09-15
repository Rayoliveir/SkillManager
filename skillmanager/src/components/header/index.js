import './styles.css'
import { useNavigate } from "react-router-dom";


function Header() {
    const navigate = useNavigate();

    const irParaHome = () => {
        navigate('/cadastro');
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

        return (
            <>
                <header>
                    <div class="nome__projeto">
                        <div>
                            <img src="images/logo-provisorio.png" alt="Logo-do-projeto" />
                        </div>
                        <div>
                            <h1>SKILLMANAGER </h1>
                            <aside>Acompanhe, Avalie, Evolua</aside>
                        </div>

                    </div>

                    <div class="redes__sociais">
                        <div>
                            <img src="images/facebook.png" alt="facebook" />
                        </div>
                        <div>
                            <img src="images/instagram.png" alt="instagram" />
                        </div>
                        <div>
                            <img src="images/twitter.png" alt="twitter" />
                        </div>
                        <div>
                            <img src="images/chat.png" alt="fale-conosco" />
                        </div>
                    </div>
                </header>

                <nav class="navegacao">
                    <dl>
                        <button onClick={irParaHome}>
                            <dt>HOME</dt>
                        </button>
                        <button onClick={irParaSobre}>
                            <dt>SOBRE</dt>
                        </button>
                        <button onClick={irParaContato}>
                            <dt>FALE CONOSCO</dt>
                        </button>
                        <button onClick={irParaLogin}>
                            <dt>LOGIN</dt>
                        </button>
                    </dl>
                </nav>
            </>
        );
    }

    export default Header