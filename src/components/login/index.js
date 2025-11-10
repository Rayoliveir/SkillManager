import './styles.css';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';

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
                </form>
            </div>
        </main>
    );
}

export default Login;