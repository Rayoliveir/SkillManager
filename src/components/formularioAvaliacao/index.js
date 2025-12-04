import React, { useState, useEffect } from 'react';
import { X, Save, AlertCircle, Star } from 'lucide-react';

const FormularioAvaliacao = ({ estagiario, initialData, onSubmit, onCancel, submetendo }) => {
    const [formData, setFormData] = useState({
        titulo: '',
        notaDesempenho: 0,
        notaHabilidadesTecnicas: 0,
        notaHabilidadesComportamentais: 0,
        feedback: ''
    });

    useEffect(() => {
        if (initialData) {
            setFormData({
                titulo: initialData.titulo || '',
                notaDesempenho: initialData.notaDesempenho || 0,
                notaHabilidadesTecnicas: initialData.notaHabilidadesTecnicas || 0,
                notaHabilidadesComportamentais: initialData.notaHabilidadesComportamentais || 0,
                feedback: initialData.feedback || ''
            });
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleStarClick = (campo, nota) => {
        setFormData(prev => ({ ...prev, [campo]: nota }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({
            ...formData,
            estagiarioId: estagiario?.id || initialData?.estagiario?.id || initialData?.estagiarioId
        });
    };

    const StarRating = ({ label, value, fieldName }) => (
        <div style={{marginBottom: '15px'}}>
            {/* FONTE MAIS FINA AQUI (500) */}
            <label style={{display:'block', fontSize:'0.9rem', marginBottom:'5px', color:'#4a5568', fontWeight:'500'}}>{label}</label>
            <div style={{display: 'flex', gap: '5px'}}>
                {[1, 2, 3, 4, 5].map((star) => (
                    <Star 
                        key={star} 
                        size={28} 
                        onClick={() => handleStarClick(fieldName, star)}
                        fill={star <= value ? "#ed8936" : "none"} 
                        color={star <= value ? "#ed8936" : "#cbd5e0"} 
                        style={{ cursor: 'pointer', transition: 'transform 0.1s' }}
                        onMouseEnter={(e) => e.currentTarget.style.transform = 'scale(1.1)'}
                        onMouseLeave={(e) => e.currentTarget.style.transform = 'scale(1)'}
                    />
                ))}
            </div>
            <div style={{fontSize: '0.8rem', color: '#718096', marginTop: '3px'}}>
                {value > 0 ? `${value}/5` : 'Selecione uma nota'}
            </div>
        </div>
    );

    const overlayStyle = {
        position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
        backgroundColor: 'rgba(22, 31, 50, 0.85)', backdropFilter: 'blur(4px)',
        display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 9999
    };

    const modalStyle = {
        backgroundColor: 'white', borderRadius: '12px', width: '95%', maxWidth: '600px',
        maxHeight: '90vh', display: 'flex', flexDirection: 'column', 
        boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.5)', overflow: 'hidden'
    };

    const headerStyle = {
        padding: '1.5rem', background: 'linear-gradient(135deg, #EA582C 0%, #F47C2A 100%)', 
        color: 'white', display: 'flex', justifyContent: 'space-between', alignItems: 'center'
    };

    const bodyStyle = {
        padding: '2rem', overflowY: 'auto', backgroundColor: '#FAFAFA'
    };

    const footerStyle = {
        padding: '1.5rem', borderTop: '1px solid #E2E8F0', display: 'flex', justifyContent: 'flex-end', gap: '10px',
        backgroundColor: 'white'
    };

    return (
        <div style={overlayStyle}>
            <div style={modalStyle}>
                <div style={headerStyle}>
                    {/* TÍTULO MAIS LEVE */}
                    <h3 style={{margin:0, fontSize:'1.25rem', fontWeight:'600'}}>
                        {initialData ? 'Editar avaliação' : 'Nova avaliação'}
                    </h3>
                    <button onClick={onCancel} style={{background:'rgba(255,255,255,0.2)', border:'none', color:'white', width:'32px', height:'32px', borderRadius:'50%', cursor:'pointer', display:'flex', alignItems:'center', justifyContent:'center'}}>
                        <X size={20} />
                    </button>
                </div>
                
                <div style={bodyStyle}>
                    <form id="form-avaliacao" onSubmit={handleSubmit}>
                        {estagiario && (
                            <div style={{marginBottom: '20px', padding: '12px', background: '#EBF8FF', borderRadius: '8px', color: '#2C5282', display: 'flex', alignItems: 'center', gap: '10px', border: '1px solid #BEE3F8'}}>
                                <AlertCircle size={20}/>
                                <span>Avaliando: <strong style={{fontWeight:'600'}}>{estagiario.nome}</strong></span>
                            </div>
                        )}

                        <div style={{marginBottom: '20px'}}>
                            {/* FONTE MAIS FINA */}
                            <label style={{display:'block', marginBottom:'8px', fontWeight:'500', color:'#2D3748'}}>Título da avaliação</label>
                            <input 
                                name="titulo" 
                                value={formData.titulo} 
                                onChange={handleChange} 
                                placeholder="Ex: Avaliação de desempenho - Maio" 
                                required 
                                style={{width: '100%', padding: '12px', border: '1px solid #CBD5E0', borderRadius: '8px', fontSize: '1rem', boxSizing:'border-box'}}
                            />
                        </div>

                        {/* TÍTULO DA SEÇÃO MAIS FINO */}
                        <h4 style={{borderBottom: '2px solid #E2E8F0', paddingBottom: '8px', marginBottom: '20px', color:'#4A5568', textTransform:'uppercase', fontSize:'0.85rem', letterSpacing:'0.05em', fontWeight:'600'}}>Critérios de avaliação</h4>
                        
                        <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'20px'}}>
                            <StarRating label="Desempenho geral" value={formData.notaDesempenho} fieldName="notaDesempenho" />
                            <StarRating label="Habilidades técnicas" value={formData.notaHabilidadesTecnicas} fieldName="notaHabilidadesTecnicas" />
                            <StarRating label="Comportamental" value={formData.notaHabilidadesComportamentais} fieldName="notaHabilidadesComportamentais" />
                        </div>

                        <div style={{marginTop: '10px'}}>
                            <label style={{display:'block', marginBottom:'8px', fontWeight:'500', color:'#2D3748'}}>Feedback detalhado</label>
                            <textarea 
                                name="feedback" 
                                rows="5" 
                                value={formData.feedback} 
                                onChange={handleChange} 
                                placeholder="Descreva os pontos fortes e as áreas de melhoria..." 
                                required 
                                style={{width: '100%', padding: '12px', border: '1px solid #CBD5E0', borderRadius: '8px', resize: 'vertical', boxSizing:'border-box', fontFamily: 'inherit', fontSize:'1rem'}} 
                            />
                        </div>
                    </form>
                </div>

                <div style={footerStyle}>
                    <button type="button" onClick={onCancel} disabled={submetendo} style={{padding:'10px 20px', border:'1px solid #CBD5E0', background:'white', color:'#2D3748', borderRadius:'6px', cursor:'pointer', fontWeight:'500'}}>Cancelar</button>
                    <button type="submit" form="form-avaliacao" disabled={submetendo} style={{padding:'10px 20px', border:'none', background:'#4299e1', color:'white', borderRadius:'6px', cursor:'pointer', display:'flex', alignItems:'center', gap:'8px', fontWeight:'500'}}>
                        {submetendo ? 'Salvando...' : <><Save size={18}/> Salvar avaliação</>}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default FormularioAvaliacao;