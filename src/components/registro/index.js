import './styles.css';
import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { User, Mail, Lock, Phone, Building, GraduationCap } from 'lucide-react';
import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';

function Registro() {
    const navigate = useNavigate();
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();
    
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        telefone: '',
        senha: '',
        confirmarSenha: '',
        tipoUsuario: 'estagiario',
        faculdade: '',
        curso: '',
        empresa: '',
        termos: false
    });
    
    const [erros, setErros] = useState({});

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
        
        // Clear error when user starts typing
        if (erros[name]) {
            setErros(prev => ({
                ...prev,
                [name]: ''
            }));
        }
    };

    const validarFormulario = () => {
        const novosErros = {};
        
        if (!formData.nome.trim()) novosErros.nome = 'Nome é obrigatório';
        if (!formData.email.trim()) {
            novosErros.email = 'E-mail é obrigatório';
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            novosErros.email = 'E-mail inválido';
        }
        if (!formData.telefone.trim()) novosErros.telefone = 'Telefone é obrigatório';
        if (!formData.senha) {
            novosErros.senha = 'Senha é obrigatória';
        } else if (formData.senha.length < 6) {
            novosErros.senha = 'Senha deve ter pelo menos 6 caracteres';
        }
        if (formData.senha !== formData.confirmarSenha) {
            novosErros.confirmarSenha = 'Senhas não coincidem';
        }
        if (!formData.termos) {
            novosErros.termos = 'Você deve aceitar os termos e condições';
        }
        
        // Campos específicos por tipo de usuário
        if (formData.tipoUsuario === 'estagiario') {
            if (!formData.faculdade.trim()) novosErros.faculdade = 'Faculdade é obrigatória';
            if (!formData.curso.trim()) novosErros.curso = 'Curso é obrigatório';
        } else if (formData.tipoUsuario === 'funcionario') {
            if (!formData.empresa.trim()) novosErros.empresa = 'Empresa é obrigatória';
        }
        
        setErros(novosErros);
        return Object.keys(novosErros).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!validarFormulario()) {
            return;
        }
        
        try {
            // Simulate API call
            await new Promise(resolve => setTimeout(resolve, 1500));
            exibirMensagem('Cadastro realizado com sucesso! Redirecionando para login...', 'sucesso');
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (err) {
            exibirMensagem('Ocorreu um erro no cadastro. Tente novamente.', 'erro');
        }
    };

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

    const titleVariants = {
        hidden: { y: 30, opacity: 0 },
        visible: { 
            y: 0, 
            opacity: 1,
            transition: { 
                type: 'spring', 
                stiffness: 150,
                damping: 12
            }
        }
    };

    return (
        <motion.main 
            className="main-registro"
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
                className="registro-container"
                variants={containerVariants}
            >
                <motion.div 
                    className="registro-header"
                    variants={titleVariants}
                >
                    <motion.img 
                        src="/assets/images/logoSemTexto.png" 
                        alt="Logo SkillManager" 
                        className="logo"
                        whileHover={{ scale: 1.05 }}
                        transition={{ type: "spring", stiffness: 400, damping: 10 }}
                    />
                    <motion.h1 variants={titleVariants}>Criar uma conta</motion.h1>
                    <motion.p variants={itemVariants}>Preencha os dados abaixo para começar</motion.p>
                </motion.div>

                <motion.form 
                    onSubmit={handleSubmit} 
                    className="registro-form"
                    variants={containerVariants}
                >
                    <motion.div className="form-section" variants={itemVariants}>
                        <h2>Informações Pessoais</h2>
                        
                        <div className="input-group">
                            <label htmlFor="nome">Nome Completo</label>
                            <div className="input-wrapper">
                                <User size={20} className="input-icon" />
                                <input
                                    type="text"
                                    id="nome"
                                    name="nome"
                                    placeholder="Seu nome completo"
                                    value={formData.nome}
                                    onChange={handleChange}
                                    className={erros.nome ? 'error' : ''}
                                />
                            </div>
                            {erros.nome && <span className="error-message">{erros.nome}</span>}
                        </div>
                        
                        <div className="input-group">
                            <label htmlFor="email">E-mail</label>
                            <div className="input-wrapper">
                                <Mail size={20} className="input-icon" />
                                <input
                                    type="email"
                                    id="email"
                                    name="email"
                                    placeholder="seu.email@exemplo.com"
                                    value={formData.email}
                                    onChange={handleChange}
                                    className={erros.email ? 'error' : ''}
                                />
                            </div>
                            {erros.email && <span className="error-message">{erros.email}</span>}
                        </div>
                        
                        <div className="input-group">
                            <label htmlFor="telefone">Telefone</label>
                            <div className="input-wrapper">
                                <Phone size={20} className="input-icon" />
                                <input
                                    type="tel"
                                    id="telefone"
                                    name="telefone"
                                    placeholder="(00) 00000-0000"
                                    value={formData.telefone}
                                    onChange={handleChange}
                                    className={erros.telefone ? 'error' : ''}
                                />
                            </div>
                            {erros.telefone && <span className="error-message">{erros.telefone}</span>}
                        </div>
                    </motion.div>
                    
                    <motion.div className="form-section" variants={itemVariants}>
                        <h2>Credenciais</h2>
                        
                        <div className="input-group">
                            <label htmlFor="senha">Senha</label>
                            <div className="input-wrapper">
                                <Lock size={20} className="input-icon" />
                                <input
                                    type="password"
                                    id="senha"
                                    name="senha"
                                    placeholder="Crie uma senha segura"
                                    value={formData.senha}
                                    onChange={handleChange}
                                    className={erros.senha ? 'error' : ''}
                                />
                            </div>
                            {erros.senha && <span className="error-message">{erros.senha}</span>}
                        </div>
                        
                        <div className="input-group">
                            <label htmlFor="confirmarSenha">Confirmar Senha</label>
                            <div className="input-wrapper">
                                <Lock size={20} className="input-icon" />
                                <input
                                    type="password"
                                    id="confirmarSenha"
                                    name="confirmarSenha"
                                    placeholder="Confirme sua senha"
                                    value={formData.confirmarSenha}
                                    onChange={handleChange}
                                    className={erros.confirmarSenha ? 'error' : ''}
                                />
                            </div>
                            {erros.confirmarSenha && <span className="error-message">{erros.confirmarSenha}</span>}
                        </div>
                    </motion.div>
                    
                    <motion.div className="form-section" variants={itemVariants}>
                        <h2>Tipo de Usuário</h2>
                        
                        <div className="radio-group">
                            <label className="radio-option">
                                <input
                                    type="radio"
                                    name="tipoUsuario"
                                    value="estagiario"
                                    checked={formData.tipoUsuario === 'estagiario'}
                                    onChange={handleChange}
                                />
                                <span className="radio-label">Estagiário</span>
                            </label>
                            
                            <label className="radio-option">
                                <input
                                    type="radio"
                                    name="tipoUsuario"
                                    value="funcionario"
                                    checked={formData.tipoUsuario === 'funcionario'}
                                    onChange={handleChange}
                                />
                                <span className="radio-label">Funcionário</span>
                            </label>
                            
                            <label className="radio-option">
                                <input
                                    type="radio"
                                    name="tipoUsuario"
                                    value="faculdade"
                                    checked={formData.tipoUsuario === 'faculdade'}
                                    onChange={handleChange}
                                />
                                <span className="radio-label">Faculdade</span>
                            </label>
                        </div>
                        
                        {formData.tipoUsuario === 'estagiario' && (
                            <motion.div 
                                className="usuario-especifico"
                                initial={{ opacity: 0, height: 0 }}
                                animate={{ opacity: 1, height: 'auto' }}
                                exit={{ opacity: 0, height: 0 }}
                                transition={{ duration: 0.3 }}
                            >
                                <div className="input-group">
                                    <label htmlFor="faculdade">Faculdade</label>
                                    <div className="input-wrapper">
                                        <Building size={20} className="input-icon" />
                                        <input
                                            type="text"
                                            id="faculdade"
                                            name="faculdade"
                                            placeholder="Nome da faculdade"
                                            value={formData.faculdade}
                                            onChange={handleChange}
                                            className={erros.faculdade ? 'error' : ''}
                                        />
                                    </div>
                                    {erros.faculdade && <span className="error-message">{erros.faculdade}</span>}
                                </div>
                                
                                <div className="input-group">
                                    <label htmlFor="curso">Curso</label>
                                    <div className="input-wrapper">
                                        <GraduationCap size={20} className="input-icon" />
                                        <input
                                            type="text"
                                            id="curso"
                                            name="curso"
                                            placeholder="Seu curso"
                                            value={formData.curso}
                                            onChange={handleChange}
                                            className={erros.curso ? 'error' : ''}
                                        />
                                    </div>
                                    {erros.curso && <span className="error-message">{erros.curso}</span>}
                                </div>
                            </motion.div>
                        )}
                        
                        {formData.tipoUsuario === 'funcionario' && (
                            <motion.div 
                                className="usuario-especifico"
                                initial={{ opacity: 0, height: 0 }}
                                animate={{ opacity: 1, height: 'auto' }}
                                exit={{ opacity: 0, height: 0 }}
                                transition={{ duration: 0.3 }}
                            >
                                <div className="input-group">
                                    <label htmlFor="empresa">Empresa</label>
                                    <div className="input-wrapper">
                                        <Building size={20} className="input-icon" />
                                        <input
                                            type="text"
                                            id="empresa"
                                            name="empresa"
                                            placeholder="Nome da empresa"
                                            value={formData.empresa}
                                            onChange={handleChange}
                                            className={erros.empresa ? 'error' : ''}
                                        />
                                    </div>
                                    {erros.empresa && <span className="error-message">{erros.empresa}</span>}
                                </div>
                            </motion.div>
                        )}
                    </motion.div>
                    
                    <motion.div className="form-section" variants={itemVariants}>
                        <div className="checkbox-group">
                            <label className="checkbox-option">
                                <input
                                    type="checkbox"
                                    name="termos"
                                    checked={formData.termos}
                                    onChange={handleChange}
                                />
                                <span className="checkbox-label">
                                    Concordo com os <a href="#">termos de uso</a> e <a href="#">política de privacidade</a>
                                </span>
                            </label>
                            {erros.termos && <span className="error-message">{erros.termos}</span>}
                        </div>
                    </motion.div>
                    
                    <motion.div className="form-actions" variants={itemVariants}>
                        <motion.button 
                            type="submit"
                            className="btn-primary"
                            whileHover={{ scale: 1.03 }}
                            whileTap={{ scale: 0.98 }}
                            transition={{ type: "spring", stiffness: 400, damping: 17 }}
                        >
                            Criar Conta
                        </motion.button>
                        
                        <p className="login-link">
                            Já tem uma conta? <Link to="/login">Faça login</Link>
                        </p>
                    </motion.div>
                </motion.form>
            </motion.div>
        </motion.main>
    );
}

export default Registro;