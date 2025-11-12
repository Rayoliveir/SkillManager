import './styles.css';

function Equipe() {
    return (
        <main className="equipe-content">
            <section className="equipe-header">
                <h1>Nossa Equipe</h1>
                <p className="subtitle">Conheça os talentos por trás do SkillManager</p>
            </section>
            
            <section className="equipe-members">
                <div className="member-card">
                    <div className="member-photo">
                        <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                    </div>
                    <div className="member-info">
                        <h3>Nome do Membro</h3>
                        <p className="role">Cargo/Função</p>
                        <p className="description">Breve descrição sobre o membro e sua contribuição para o projeto.</p>
                    </div>
                </div>
                
                <div className="member-card">
                    <div className="member-photo">
                        <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                    </div>
                    <div className="member-info">
                        <h3>Nome do Membro</h3>
                        <p className="role">Cargo/Função</p>
                        <p className="description">Breve descrição sobre o membro e sua contribuição para o projeto.</p>
                    </div>
                </div>
                
                <div className="member-card">
                    <div className="member-photo">
                        <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                    </div>
                    <div className="member-info">
                        <h3>Nome do Membro</h3>
                        <p className="role">Cargo/Função</p>
                        <p className="description">Breve descrição sobre o membro e sua contribuição para o projeto.</p>
                    </div>
                </div>
                
                <div className="member-card">
                    <div className="member-photo">
                        <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                    </div>
                    <div className="member-info">
                        <h3>Nome do Membro</h3>
                        <p className="role">Cargo/Função</p>
                        <p className="description">Breve descrição sobre o membro e sua contribuição para o projeto.</p>
                    </div>
                </div>
                
                <div className="member-card">
                    <div className="member-photo">
                        <img src="/assets/images/default-avatar.png" alt="Membro da equipe" />
                    </div>
                    <div className="member-info">
                        <h3>Nome do Membro</h3>
                        <p className="role">Cargo/Função</p>
                        <p className="description">Breve descrição sobre o membro e sua contribuição para o projeto.</p>
                    </div>
                </div>
            </section>
        </main>
    );
}

export default Equipe;