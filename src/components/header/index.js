import './styles.css'
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { Menu, X, Facebook, Instagram, Twitter, MessageCircle, Home, Users, Phone, LogIn, UserPlus } from "lucide-react";

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
                    {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
                </button>

                <div className={`redes__sociais ${isMenuOpen ? 'mobile-hidden' : ''}`}>
                    <div>
                        <Facebook size={20} />
                    </div>
                    <div>
                        <Instagram size={20} />
                    </div>
                    <div>
                        <Twitter size={20} />
                    </div>
                    <div>
                        <MessageCircle size={20} />
                    </div>
                </div>
            </header>

            <nav className={`navegacao ${isMenuOpen ? 'active' : ''}`}>
                <dl>
                    <button onClick={irParaHome}>
                        <dt><Home size={16} /> HOME</dt>
                    </button>
                    <button onClick={irParaQuemSomos}>
                        <dt><Users size={16} /> QUEM SOMOS</dt>
                    </button>
                    <button onClick={irParaContato}>
                        <dt><Phone size={16} /> FALE CONOSCO</dt>
                    </button>
                    <button onClick={irParaLogin}>
                        <dt><LogIn size={16} /> LOGIN</dt>
                    </button>
                    <button onClick={irParaCadastro}>
                        <dt><UserPlus size={16} /> CADASTRO</dt>
                    </button>
                </dl>
            </nav>
        </>
    );
}

export default Header