import './styles.css';
import Header from '../../components/header';
import EsqueciSenha from '../../components/esqueciSenha';

function TelaEsqueciSenha() {
    return (
        <div className="pag_esqueci-senha-container">
            <Header />
            <EsqueciSenha />
        </div>
    );
}

export default TelaEsqueciSenha;