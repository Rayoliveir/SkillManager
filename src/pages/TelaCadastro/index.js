import './styles.css';
import Header from '../../components/header';
import Cadastro from '../../components/cadastro/index.js';

function TelaCadastro() {
    return(
        <div className="pag_cadastro-container">
            <Header />
            { <Cadastro />}
        </div>
    );
}

export default TelaCadastro;