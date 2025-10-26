import './styles.css';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import { DEV_USERS, isDevMode } from '../../utils/devUsers';

function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const { login } = useAuth();
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    async function handleLogin(e) {
        e.preventDefault();

        if (!email || !senha) {
            exibirMensagem('Preencha todos os campos para continuar.', 'erro');
            return;
        }
        
        try {
            await login(email, senha);
        } catch (error) {
            exibirMensagem('Login ou senha incorretos. Tente novamente.', 'erro');
        }
    }

    // Function to auto-fill login form with dev user credentials
    const fillDevUser = (userType) => {
        const user = DEV_USERS[userType];
        if (user) {
            setEmail(user.email);
            setSenha(user.senha);
        }
    };

    return (
        <main className="main-login">
            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            <div className="gilson">
                <div className="logo-skill">
                    <img src="/assets/images/logoSemTexto.png" alt="logoSemTexto" />
                    <h2>Faça login na sua conta</h2>
                </div>

                <form onSubmit={handleLogin} className="form-login">
                    <label>E-mail ou CNPJ: <br />
                        <input
                            type="text"
                            name="email"
                            id="email"
                            placeholder="Digite seu e-mail"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </label>

                    <label>Senha: <br />
                        <input
                            type="password"
                            name="senha"
                            id="senha"
                            placeholder="Digite sua senha"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                        />
                    </label>

                    <div>
                        <button type="submit">Entrar</button>
                    </div>
                    <p>Não tem uma conta? Crie sua conta <Link to="/cadastro">aqui</Link></p>
                    
                    {/* Development users section - only visible in development mode */}
                    {isDevMode() && (
                        <div className="dev-users-section">
                            <h3>Usuários para Desenvolvimento:</h3>
                            <div className="dev-users-list">
                                <button 
                                    type="button" 
                                    className="dev-user-btn"
                                    onClick={() => fillDevUser('estagiario')}
                                >
                                    Estagiário: {DEV_USERS.estagiario.email} / {DEV_USERS.estagiario.senha}
                                </button>
                                <button 
                                    type="button" 
                                    className="dev-user-btn"
                                    onClick={() => fillDevUser('faculdade')}
                                >
                                    Faculdade: {DEV_USERS.faculdade.email} / {DEV_USERS.faculdade.senha}
                                </button>
                                <button 
                                    type="button" 
                                    className="dev-user-btn"
                                    onClick={() => fillDevUser('funcionario')}
                                >
                                    Funcionário: {DEV_USERS.funcionario.email} / {DEV_USERS.funcionario.senha}
                                </button>
                            </div>
                        </div>
                    )}
                </form>
            </div>
        </main>
    );
}

export default Login;