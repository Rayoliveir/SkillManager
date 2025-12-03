// Equipe.jsx
import { useRef, useEffect, useState } from 'react';
import './styles.css';
import { Linkedin, Github } from "lucide-react";


function Equipe() {
    const containerRef = useRef(null);
    const [canScrollLeft, setCanScrollLeft] = useState(false);
    const [canScrollRight, setCanScrollRight] = useState(false);

    useEffect(() => {
        const el = containerRef.current;
        if (!el) return;

        const updateButtons = () => {
            setCanScrollLeft(el.scrollLeft > 0);
            setCanScrollRight(el.scrollLeft + el.clientWidth < el.scrollWidth - 1);
        };

        updateButtons();
        el.addEventListener('scroll', updateButtons);
        window.addEventListener('resize', updateButtons);

        return () => {
            el.removeEventListener('scroll', updateButtons);
            window.removeEventListener('resize', updateButtons);
        };
    }, []);

    const scrollByCard = (direction = 'right') => {
        const el = containerRef.current;
        if (!el) return;
        const card = el.querySelector('.member-card');
        const cardStyle = window.getComputedStyle(card);
        const gap = 20; // deve bater com o gap no CSS
        const cardWidth = card ? card.clientWidth + parseInt(cardStyle.marginRight || 0) : 300;
        const amount = (cardWidth + gap) * (direction === 'right' ? 1 : -1);
        el.scrollBy({ left: amount, behavior: 'smooth' });
    };

    // keyboard accessibility for arrows (left/right)
    useEffect(() => {
        const handler = (e) => {
            if (e.key === 'ArrowRight') scrollByCard('right');
            if (e.key === 'ArrowLeft') scrollByCard('left');
        };
        window.addEventListener('keydown', handler);
        return () => window.removeEventListener('keydown', handler);
    }, []);

    return (
        <main className="equipe-content">
            <section className="equipe-header">
                <h1>Nossa equipe</h1>
                <p className="subtitle">Conheça os talentos por trás do SkillManager</p>
            </section>

            <div className="equipe-members-wrapper">
                <button
                    className={`arrow-left ${canScrollLeft ? 'visible' : 'hidden'}`}
                    aria-label="rolar para a esquerda"
                    onClick={() => scrollByCard('left')}
                >
                    &#10094;
                </button>

                <section className="equipe-members" ref={containerRef}>
                    <div className="member-card">
                        <div className="member-photo">
                            <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                        </div>
                        <div className="member-info">
                            <h3>Marcelly Oliveira</h3>
                            <p className="role">Desenvolvedor</p>
                            <p className="description">Estudante de tecnologia que gosta de transformar curiosidade em prática e explorar novas ferramentas.</p>
                        </div>
                           <div className="member-links">
                            <a href="https://www.linkedin.com/in/marcelly-oliveira-4a89a3301/" target="_blank" rel="noopener noreferrer" className="link-btn linkedin">
                                <Linkedin size={18} />
                            </a>
                            <a href="https://github.com/Rayoliveir" target="_blank" rel="noopener noreferrer" className="link-btn github">
                                <Github size={18} />
                            </a>
                        </div>
                    </div>

                    <div className="member-card">
                        <div className="member-photo">
                            <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                        </div>
                        <div className="member-info">
                            <h3>Michel Linhares</h3>
                            <p className="role">Desenvolvedor</p>
                            <p className="description">Apaixonado por tecnologia, está sempre estudando e se aprimorando para dar o próximo passo na área tech.</p>
                        </div>
                           <div className="member-links">
                            <a href="https://www.linkedin.com/in/michel-linhares-973b181b3/" target="_blank" rel="noopener noreferrer" className="link-btn linkedin">
                                <Linkedin size={18} />
                            </a>
                            <a href="https://github.com/Chechel01" target="_blank" rel="noopener noreferrer" className="link-btn github">
                                <Github size={18} />
                            </a>
                        </div>
                    </div>

                    <div className="member-card">
                        <div className="member-photo">
                            <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                        </div>
                        <div className="member-info">
                            <h3>Natália França</h3>
                            <p className="role">Desenvolvedor</p>
                            <p className="description">Interessada em tecnologia, segue se dedicando aos estudos e ao seu crescimento prossional na área de T.I.</p>
                        </div>
                           <div className="member-links">
                            <a href="https://www.linkedin.com/in/nat%C3%A1lia-fran%C3%A7a-lima-a934b5398/" target="_blank" rel="noopener noreferrer" className="link-btn linkedin">
                                <Linkedin size={18} />
                            </a>
                            <a href="https://github.com/LimaNat" target="_blank" rel="noopener noreferrer" className="link-btn github">
                                <Github size={18} />
                            </a>
                        </div>
                    </div>

                    <div className="member-card">
                        <div className="member-photo">
                            <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                        </div>
                        <div className="member-info">
                            <h3>Rhana Souza</h3>
                            <p className="role">Desenvolvedor</p>
                            <p className="description">Estudante motivada na área de tecnologia, sempre em busca de novos aprendizados e evolução profissional.</p>
                        </div>
                           <div className="member-links">
                            <a href="https://www.linkedin.com/in/rhana-santos-94a6652a4" target="_blank" rel="noopener noreferrer" className="link-btn linkedin">
                                <Linkedin size={18} />
                            </a>
                            <a href="https://github.com/RHAN4" target="_blank" rel="noopener noreferrer" className="link-btn github">
                                <Github size={18} />
                            </a>
                        </div>
                    </div>

                    <div className="member-card">
                        <div className="member-photo">
                            <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                        </div>
                        <div className="member-info">
                            <h3>Tiago Santana</h3>
                            <p className="role">Desenvolvedor</p>
                            <p className="description">Jovem em formação na área de Desenvolvimento de Sistemas, com vontade constante de aprender e se desenvolver.</p>
                        </div>
                        <div className="member-links">
                            <a href="https://www.linkedin.com/in/tiago-santana-545058329/" target="_blank" rel="noopener noreferrer" className="link-btn linkedin">
                                <Linkedin size={18} />
                            </a>
                            <a href="https://github.com/S4NTTANA" target="_blank" rel="noopener noreferrer" className="link-btn github">
                                <Github size={18} />
                            </a>
                        </div>
                    </div>
                </section>

                <button
                    className={`arrow-right ${canScrollRight ? 'visible' : 'hidden'}`}
                    aria-label="rolar para a direita"
                    onClick={() => scrollByCard('right')}
                >
                    &#10095;
                </button>
            </div>
        </main>
    );
}

export default Equipe;
