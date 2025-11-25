import './styles.css';
import Header from '../../components/header';
import Cadastro from '../../components/cadastro/index.js';
import Footer from '../../components/footer';

function TelaCadastro() {
    return(
        <div className="pag_cadastro-container">
            <Header />
            <Cadastro />
            <Footer />
        </div>
    );
}

export default TelaCadastro;