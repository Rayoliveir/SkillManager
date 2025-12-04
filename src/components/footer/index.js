import React from 'react';
import './style.css';
// import { Facebook, Instagram, Twitter, Linkedin } from 'lucide-react';

function Footer() {
    return (
        <footer className="footer">
            <div className="footer-content">
                <div className="footer-section">
                    <h3>SkillManager</h3>
                    <p className="footer-description">
                        Plataforma completa para gerenciamento de estagiários, 
                        com avaliações personalizadas e feedback contínuo.
                    </p>
                    {/* <div className="social-links">
                        <a href="#" className="social-link" target="_blank" rel="noopener noreferrer">
                            <Facebook size={24} color="white" />
                        </a>
                        <a href="#" className="social-link" target="_blank" rel="noopener noreferrer">
                            <Instagram size={24} color="white" />
                        </a>
                        <a href="#" className="social-link" target="_blank" rel="noopener noreferrer">
                            <Twitter size={24} color="white" />
                        </a>
                        <a href="#" className="social-link" target="_blank" rel="noopener noreferrer">
                            <Linkedin size={24} color="white" />
                        </a>
                    </div> */}
                </div>
                
                <div className="footer-section">
                    <h4>Links rápidos</h4>
                    <ul>
                        <li><a href="/">Home</a></li>
                        <li><a href="/quem-somos">Quem somos</a></li>
                        <li><a href="/contato">Fale conosco</a></li>
                        <li><a href="/login">Login</a></li>
                        <li><a href="/cadastro">Cadastro</a></li>
                    </ul>
                </div>
                
                <div className="footer-section">
                    <h4>Contato</h4>
                    <ul>
                        <li>contato@skillmanager.com</li>
                        <li>(71) 98317-2382</li>
                        <li>Salvador, Bahia</li>
                    </ul>
                </div>
            </div>
            
            <div className="footer-bottom">
                <p>© 2025 SkillManager. Todos os direitos reservados.</p>
            </div>
        </footer>
    );
}

export default Footer;