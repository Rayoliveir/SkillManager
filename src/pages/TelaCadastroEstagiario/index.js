import './styles.css';
import Header from '../../components/header';
import CadastroEstagiario from '../../components/cadastroEstagiario';

function TelaCadastroEstagiario() {
    return(
        <div className="pag_cadastroEstagiario-container">
            <Header />
            {<CadastroEstagiario />}
        </div>
    );
}

export default TelaCadastroEstagiario;