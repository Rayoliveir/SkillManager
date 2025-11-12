import './style.css';

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
                    <div className="social-links">
                        <a href="#" className="social-link">
                            <img src="/assets/images/facebook.png" alt="Facebook" />
                        </a>
                        <a href="#" className="social-link">
                            <img src="/assets/images/instagram.png" alt="Instagram" />
                        </a>
                        <a href="#" className="social-link">
                            <img src="/assets/images/twitter.png" alt="Twitter" />
                        </a>
                        <a href="#" className="social-link">
                            <img src="/assets/images/linkedin.png" alt="LinkedIn" />
                        </a>
                    </div>
                </div>
                
                <div className="footer-section">
                    <h4>Links Rápidos</h4>
                    <ul>
                        <li><a href="/">Home</a></li>
                        <li><a href="/quem-somos">Quem Somos</a></li>
                        <li><a href="/contato">Fale Conosco</a></li>
                        <li><a href="/login">Login</a></li>
                        <li><a href="/cadastro">Cadastro</a></li>
                    </ul>
                </div>
                
                <div className="footer-section">
                    <h4>Contato</h4>
                    <ul>
                        <li>contato@skillmanager.com</li>
                        <li>(71) 99999-9999</li>
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