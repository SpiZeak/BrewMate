import Avatar from '@components/Avatar';
import Typography from '@mui/material/Typography';

const Profile = () => {
  return (
    <>
      <Avatar />
      <Typography variant='h2'>Profile</Typography>
      <Typography variant='body1'>This is the user's profile page.</Typography>
    </>
  );
};

export default Profile;
