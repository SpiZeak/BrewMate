import Avatar from '@components/Avatar';
import { clearUser } from '@features/user/userSlice';
import { Button } from '@mui/material';
import Typography from '@mui/material/Typography';
import { useDispatch } from 'react-redux';

const Profile = () => {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(clearUser());
  };

  return (
    <>
      <Avatar />
      <Typography variant='h2'>Profile</Typography>
      <Typography variant='body1'>This is the user's profile page.</Typography>
      <Button variant='outlined' color='error' onClick={handleLogout}>
        Log out
      </Button>
    </>
  );
};

export default Profile;
