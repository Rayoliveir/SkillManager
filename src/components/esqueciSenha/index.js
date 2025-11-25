import { useState } from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';
import './styles.css';

function EsqueciSenha() {
    const [email, setEmail] = useState('');
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    async function handleResetPassword(e) {
        e.preventDefault();

        if (!email) {
            exibirMensagem('Por favor, informe seu e-mail.', 'erro');
            return;
        }

        // Simple email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            exibirMensagem('Por favor, informe um e-mail válido.', 'erro');
            return;
        }

        try {
            // Simulate API call
            await new Promise(resolve => setTimeout(resolve, 1000));
            exibirMensagem('Instruções para redefinição de senha foram enviadas para o seu e-mail.', 'sucesso');
            setEmail('');
        } catch (error) {
            console.error("Erro ao redefinir senha:", error);
            exibirMensagem('Ocorreu um erro. Tente novamente mais tarde.', 'erro');
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
            className="main-esqueci-senha"
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
                className="esqueci-senha-container"
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
                    <motion.h2 variants={itemVariants}>Redefinir senha</motion.h2>
                </motion.div>

                <motion.form 
                    onSubmit={handleResetPassword} 
                    className="form-esqueci-senha"
                    variants={containerVariants}
                >
                    <motion.p variants={itemVariants} className="instruction-text">
                        Informe seu e-mail para receber as instruções de redefinição de senha.
                    </motion.p>
                    
                    <motion.label variants={itemVariants}>
                        E-mail:
                        <motion.input
                            type="email"
                            name="email"
                            id="email"
                            placeholder="Digite seu e-mail"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
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
                            Enviar instruções
                        </motion.button>
                    </motion.div>
                    
                    <motion.p variants={itemVariants} className="back-to-login">
                        <Link to="/login">Voltar para o login</Link>
                    </motion.p>
                </motion.form>
            </motion.div>
        </motion.main>
    );
}

export default EsqueciSenha;