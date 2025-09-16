import './styles.css';
import Header from '../../components/header';
import Login from '../../components/login';

function TelaLogin() {
    return(
        <div className="pag_login-container">
            <Header />
            { <Login />}
        </div>
    );
}

export default TelaLogin;