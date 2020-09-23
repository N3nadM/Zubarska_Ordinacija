import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import DateFnsUtils from "@date-io/date-fns";
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import Axios from "axios";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import Grid from "@material-ui/core/Grid";
import InputLabel from "@material-ui/core/InputLabel";
import TextField from "@material-ui/core/TextField";
import FormControl from "@material-ui/core/FormControl";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import DialogTitle from "@material-ui/core/DialogTitle";
import ListItemText from "@material-ui/core/ListItemText";
import Typography from "@material-ui/core/Typography";
import { connect } from "react-redux";

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
}));

const MakeAppointment = ({ currentUser }) => {
  const classes = useStyles();
  const [selectedDate, handleDateChange] = useState(new Date());
  const [selectedDateTime, setSelectedDateTime] = useState(null);
  const [avaibleTimes, setAvaibleTimes] = React.useState([]);
  const [duration, setDuration] = React.useState("");
  const [state, setState] = React.useState({
    username:
      currentUser?.user?.role[0].name === "ROLE_PATIENT"
        ? currentUser.user.username
        : "",
    phoneNumber: "",
  });

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await Axios.post("/api/appointment", {
      username: state.username,
      phoneNumber: state.phoneNumber,
      duration,
      selectedDateTime: selectedDateTime.replace(
        selectedDateTime.substring(19, 24),
        ""
      ),
    });
    if (res.status === 200) {
      setState("");
      setSelectedDateTime("");
      setDuration("");
    }
  };

  const handleListItemClick = (value) => {
    setSelectedDateTime(value);
    setOpen(false);
  };

  const handleChangeDuration = (e) => {
    console.log(e.target.value);
    setDuration(e.target.value);
  };

  const getAvailableTime = async (date) => {
    handleDateChange(date);
    try {
      const { data } = await Axios.post("/api/appointment/available", {
        date,
        duration,
      });
      setAvaibleTimes(data);
      setOpen(true);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <div>
        <Grid container spacing={2} direction="row">
          <Grid item sm={5}>
            <Grid item>
              <FormControl className={classes.formControl}>
                <TextField
                  disabled={currentUser?.user?.role[0].name === "ROLE_PATIENT"}
                  value={state.username}
                  onChange={handleChange}
                  variant="outlined"
                  margin="normal"
                  required
                  id="username"
                  label="Username"
                  name="username"
                  autoFocus
                />
                <TextField
                  value={state.phoneNumber}
                  onChange={handleChange}
                  variant="outlined"
                  margin="normal"
                  required
                  id="phoneNumber"
                  label="Phone number"
                  name="phoneNumber"
                />
              </FormControl>
            </Grid>
          </Grid>
          <Grid item sm={6}>
            <Grid item style={{ padding: 10 }}>
              <FormControl className={classes.formControl}>
                <InputLabel>Duration</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={duration}
                  onChange={handleChangeDuration}
                >
                  <MenuItem value={30}>30 Minutes</MenuItem>
                  <MenuItem value={60}>60 Minutes</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item style={{ padding: 10 }}>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                  disabled={duration === ""}
                  clearable
                  label="Select appointment date"
                  value={selectedDate}
                  placeholder="10/10/2018"
                  onChange={getAvailableTime}
                  minDate={new Date()}
                  format="MM/dd/yyyy"
                />
              </MuiPickersUtilsProvider>
            </Grid>
            <Grid item style={{ padding: 10 }}>
              <Typography variant="subtitle1">
                Selected Date and Time: {selectedDateTime}
              </Typography>
            </Grid>
          </Grid>
        </Grid>
        <Button
          onClick={handleSubmit}
          variant="contained"
          color="primary"
          id="submit"
        >
          Reserve
        </Button>
      </div>
      <Dialog
        onClose={handleClose}
        aria-labelledby="simple-dialog-title"
        open={open}
      >
        <DialogTitle id="simple-dialog-title">
          Choose the time to reserve appointment
        </DialogTitle>
        <List>
          {avaibleTimes &&
            avaibleTimes.sort().map((time) => (
              <ListItem
                button
                onClick={() => handleListItemClick(time)}
                key={time}
              >
                <ListItemText primary={time} />
              </ListItem>
            ))}
        </List>
      </Dialog>
    </>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.currentUser,
});

export default connect(mapStateToProps, null)(MakeAppointment);
