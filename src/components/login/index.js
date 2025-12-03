import './styles.css';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    // --- INFO: O 'login' vem do 'context/AuthContext.js' ---
    const { login } = useAuth(); 
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    async function handleLogin(e) {
        e.preventDefault();

        if (!username || !password) {
            exibirMensagem('Preencha todos os campos para continuar.', 'erro');
            return;
        }
        
        try {
            await login(username, password);
            // O redirecionamento é feito pelo AuthContext
        } catch (error) {
            // --- CORREÇÃO: Mostra a mensagem de erro real vinda da API
            // ex: "Email ou senha inválidos."
            console.error("Erro no login:", error.message);
            exibirMensagem(error.message, 'erro');
        }
    }

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
                    <label>Usuário: <br />
                        <input
                            type="text"
                            name="username"
                            id="username"
                            placeholder="Digite seu email de usuário"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </label>

                    <label>Senha: <br />
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="Digite sua senha"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </label>

                    <div>
                        <button type="submit">Entrar</button>
                    </div>
                    <p>Não tem uma conta? Crie sua conta <Link to="/cadastro">aqui</Link></p>
                </form>
            </div>
        </main>
    );
}

export default Login;