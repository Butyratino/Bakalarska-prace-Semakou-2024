import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WelcomeForm from './components/WelcomeForm';
import { UserProvider } from './components/UserContext'; 
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginForm';
import HomeForm from './components/Main';


function App() {
  return (
    <Router>
      <UserProvider> {/* Wrap the entire app with UserProvider */}
        <Routes>
          <Route path="/" element={<WelcomeForm />} />
          <Route path="/registration" element={<RegistrationForm />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/home" element={<HomeForm />} />
          
          {/* <Route path="/sections" element={<Layout><SectionsForm /></Layout>} />
          <Route path="/addservices" element={<Layout><AddServicesForm /></Layout>} />
          <Route path="/attractions" element={<Layout><AttractionsForm /></Layout>} />
          <Route path="/users" element={<Layout><UserForm /></Layout>} />
          <Route path="/sidebar" element={<Layout><UserProfile /></Layout>} />
          <Route path="/tickets" element={<Layout><TicketsForm /></Layout>} />
          <Route path="/payments" element={<Layout><PaymentsForm /></Layout>} />
          <Route path="/employees" element={<Layout><EmployeesForm /></Layout>} />
          <Route path="/schedules" element={<Layout><SchedulesForm /></Layout>} /> */}

        </Routes>
      </UserProvider>
    </Router>
  );
}

export default App;
