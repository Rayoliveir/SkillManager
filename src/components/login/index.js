import { useState } from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useAuth } from '../../context/AuthContext';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import './styles.css';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
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
        } catch (error) {
            console.error("Erro no login:", error);
            exibirMensagem('Credenciais incorretas. Tente novamente.', 'erro');
        }
    }

    // Animation variants
    const containerVariants = {
        hidden: { opacity: 0 },
        visible: { 
            opacity: 1,
            transition: { 
                staggerChildren: 0.1,
                delayChildren: 0.2
            }
        }
    };

    const itemVariants = {
        hidden: { y: 20, opacity: 0 },
        visible: { 
            y: 0, 
            opacity: 1,
            transition: { 
                type: 'spring', 
                stiffness: 100 
            }
        }
    };

    const logoVariants = {
        hidden: { scale: 0.8, opacity: 0 },
        visible: { 
            scale: 1, 
            opacity: 1,
            transition: { 
                type: 'spring', 
                stiffness: 200,
                damping: 10
            }
        },
        hover: { 
            scale: 1.05,
            transition: { 
                type: 'spring', 
                stiffness: 400, 
                damping: 10 
            }
        }
    };

    return (
        <motion.main 
            className="main-login"
            initial="hidden"
            animate="visible"
            variants={containerVariants}
        >
            {/* Animated background elements */}
            <div className="background-elements">
                <motion.div 
                    className="circle circle-1"
                    animate={{ 
                        y: [0, -20, 0],
                        x: [0, 10, 0]
                    }}
                    transition={{ 
                        duration: 6, 
                        repeat: Infinity, 
                        ease: "easeInOut" 
                    }}
                />
                <motion.div 
                    className="circle circle-2"
                    animate={{ 
                        y: [0, 30, 0],
                        x: [0, -15, 0]
                    }}
                    transition={{ 
                        duration: 8, 
                        repeat: Infinity, 
                        ease: "easeInOut",
                        delay: 1
                    }}
                />
                <motion.div 
                    className="circle circle-3"
                    animate={{ 
                        y: [0, -25, 0],
                        x: [0, 20, 0]
                    }}
                    transition={{ 
                        duration: 7, 
                        repeat: Infinity, 
                        ease: "easeInOut",
                        delay: 2
                    }}
                />
            </div>

            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            
            <motion.div 
                className="login-container"
                variants={containerVariants}
            >
                <motion.div 
                    className="logo-skill"
                    variants={logoVariants}
                    whileHover="hover"
                >
                    <motion.img 
                        src="/assets/images/logoSemTexto.png" 
                        alt="logoSemTexto" 
                        variants={logoVariants}
                        whileHover="hover"
                    />
                    <motion.h2 variants={itemVariants}>Faça login na sua conta</motion.h2>
                </motion.div>

                <motion.form 
                    onSubmit={handleLogin} 
                    className="form-login"
                    variants={containerVariants}
                >
                    <motion.label variants={itemVariants}>
                        Usuário:
                        <motion.input
                            type="text"
                            name="username"
                            id="username"
                            placeholder="Digite seu nome de usuário"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            whileFocus={{ scale: 1.02 }}
                            transition={{ type: "spring", stiffness: 300 }}
                        />
                    </motion.label>

                    <motion.label variants={itemVariants}>
                        Senha:
                        <motion.input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="Digite sua senha"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            whileFocus={{ scale: 1.02 }}
                            transition={{ type: "spring", stiffness: 300 }}
                        />
                    </motion.label>

                    <motion.div variants={itemVariants}>
                        <motion.button 
                            type="submit"
                            whileHover={{ scale: 1.03 }}
                            whileTap={{ scale: 0.98 }}
                            transition={{ type: "spring", stiffness: 400, damping: 17 }}
                        >
                            Entrar
                        </motion.button>
                    </motion.div>
                    
                    <motion.p variants={itemVariants}>
                        Não tem uma conta? Crie sua conta <Link to="/cadastro">aqui</Link>
                    </motion.p>
                    
                    <motion.p variants={itemVariants} className="forgot-password">
                        <Link to="/esqueci-senha">Esqueci minha senha</Link>
                    </motion.p>
                </motion.form>
            </motion.div>
        </motion.main>
    );
}

export default Login;