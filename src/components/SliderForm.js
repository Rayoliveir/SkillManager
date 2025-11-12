import React, { useState } from 'react';
import './SliderForm.css';

const SliderForm = ({ steps, onSubmit, formData, handleChange }) => {
  const [currentStep, setCurrentStep] = useState(0);

  const nextStep = () => {
    if (currentStep < steps.length - 1) {
      setCurrentStep(currentStep + 1);
    }
  };

  const prevStep = () => {
    if (currentStep > 0) {
      setCurrentStep(currentStep - 1);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(e);
  };

  return (
    <div className="slider-form-container">
      <div className="slider-progress-bar">
        <div 
          className="slider-progress-fill" 
          style={{ width: `${((currentStep + 1) / steps.length) * 100}%` }}
        ></div>
        <div className="slider-step-indicators">
          {steps.map((step, index) => (
            <div 
              key={index} 
              className={`slider-step-indicator ${index === currentStep ? 'active' : ''}`}
            >
              <span>{index + 1}</span>
            </div>
          ))}
        </div>
      </div>

      <form onSubmit={handleSubmit} className="slider-form">
        <div className="slider-step-content">
          {steps[currentStep].content}
        </div>

        <div className="slider-navigation">
          {currentStep > 0 && (
            <button 
              type="button" 
              onClick={prevStep} 
              className="slider-nav-btn prev-btn"
            >
              Voltar
            </button>
          )}
          
          {currentStep < steps.length - 1 ? (
            <button 
              type="button" 
              onClick={nextStep} 
              className="slider-nav-btn next-btn"
            >
              Próximo
            </button>
          ) : (
            <button 
              type="submit" 
              className="slider-nav-btn submit-btn"
            >
              Cadastrar
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default SliderForm;