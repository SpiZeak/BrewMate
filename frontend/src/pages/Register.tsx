import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';

const Register = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    console.log(formData);
  }

  return (
    <Box
      component='form'
      onSubmit={handleSubmit}
      className='flex flex-col w-full max-w-sm mx-auto mt-8 p-4 shadow-md rounded space-y-4'
    >
      <Typography variant='h5'>Registration</Typography>
      <TextField
        type='email'
        label='Email'
        variant='outlined'
        required
        onChange={e => setEmail(e.target.value)}
        className='mb-4'
        name='email'
      />
      <TextField
        type='password'
        label='Password'
        variant='outlined'
        required
        onChange={e => setPassword(e.target.value)}
        name='password'
      />
      <Button type='submit' variant='contained' disabled={!email || !password}>
        Submit
      </Button>
    </Box>
  );
};

export default Register;
