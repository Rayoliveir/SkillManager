import './styles.css'

function Header() {
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
                    <dt><a href="#">HOME</a></dt>
                    <dt><a href="#">SOBRE</a></dt>
                    <dt><a href="#">FALE CONOSCO</a></dt>
                    <dt><a href="#">CONTATO</a></dt>
                </dl>
            </nav>
        </>
    );
}