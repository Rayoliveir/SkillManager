// Development-only fixed users for testing purposes
// These users are only for development and should not be used in production

export const DEV_USERS = {
  estagiario: {
    email: 'estagiario@teste.com',
    senha: 'senha123',
    nome: 'João Silva',
    tipo: 'estagiario'
  },
  faculdade: {
    email: 'faculdade@teste.com',
    senha: 'senha123',
    nome: 'Faculdade Teste',
    tipo: 'faculdade'
  },
  funcionario: {
    email: 'funcionario@teste.com',
    senha: 'senha123',
    nome: 'Maria Santos',
    tipo: 'funcionario'
  }
};

// Function to simulate login for development
export const simulateDevLogin = (email, senha) => {
  // Check if credentials match any dev user
  const user = Object.values(DEV_USERS).find(
    user => user.email === email && user.senha === senha
  );

  if (user) {
    return {
      username: user.email,
      roles: [`ROLE_${user.tipo.toUpperCase()}`],
      nome: user.nome
    };
  }

  return null;
};

// Function to check if we're in development mode
export const isDevMode = () => {
  return process.env.NODE_ENV === 'development';
};

const devUsers = { DEV_USERS, simulateDevLogin, isDevMode };

export default devUsers;