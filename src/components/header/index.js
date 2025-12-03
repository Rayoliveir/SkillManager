import './styles.css'
import { useNavigate } from "react-router-dom";
import { useState } from "react";

function Header() {
    const navigate = useNavigate();
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const irParaHome = () => {
        navigate('/');
        setIsMenuOpen(false);
    }
    
    const irParaQuemSomos = () => {
        navigate('/quem-somos');
        setIsMenuOpen(false);
    }
    
    const irParaContato = () => {
        navigate('/contato');
        setIsMenuOpen(false);
    }
    
    const irParaLogin = () => {
        navigate('/login');
        setIsMenuOpen(false);
    }
    
    const irParaCadastro = () => {
        navigate('/cadastro');
        setIsMenuOpen(false);
    }
    
    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
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

                <button className="menu-toggle" onClick={toggleMenu}>
                    {isMenuOpen ? '✕' : '☰'}
                </button>

                {/* <div className={`redes__sociais ${isMenuOpen ? 'mobile-hidden' : ''}`}>
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
                </div> */}
            </header>

            <nav className={`navegacao ${isMenuOpen ? 'active' : ''}`}>
                <dl>
                    <button onClick={irParaHome}>
                        <dt>HOME</dt>
                    </button>
                    <button onClick={irParaQuemSomos}>
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