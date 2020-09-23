import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Divider from "@material-ui/core/Divider";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Drawer from "@material-ui/core/Drawer";
import TabPanel from "../Tabs/TabPanel";
import { connect } from "react-redux";

import Profile from "../pages/Profile";
import MakeAppointment from "../pages/MakeAppointment";
import MyAppointments from "../pages/MyAppointments";
import MyCalendar from "../pages/MyCalendar";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  toolbar: theme.mixins.toolbar,
}));
function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

const Home = ({ currentUser }) => {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const [open, setOpen] = React.useState(false);
  const classes = useStyles();
  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const drawer = (
    <div>
      <div className={classes.toolbar} />
      <Divider />
      <Tabs
        orientation="vertical"
        value={value}
        onChange={handleChange}
        variant="scrollable"
        aria-label="simple tabs example"
      >
        <Tab label="Profile" {...a11yProps(0)} />
        <Tab label="Make appointment" {...a11yProps(1)} />
        {currentUser?.user?.role[0].name === "ROLE_PATIENT" && (
          <Tab label="My appointments" {...a11yProps(2)} />
        )}
        {currentUser?.user?.role[0].name === "ROLE_DENTIST" && (
          <Tab label="My calendar" {...a11yProps(2)} />
        )}
      </Tabs>
    </div>
  );

  return (
    <>
      <div className={classes.root}>
        <nav className={classes.drawer} aria-label="mailbox folders">
          <Drawer
            classes={{
              paper: classes.drawerPaper,
            }}
            variant="permanent"
            open
          >
            {drawer}
          </Drawer>
        </nav>
        <main className={classes.content}>
          <TabPanel value={value} index={0}>
            <Profile />
          </TabPanel>
          <TabPanel value={value} index={1}>
            {value === 1 && <MakeAppointment />}
          </TabPanel>
          {currentUser?.user?.role[0].name === "ROLE_PATIENT" && (
            <TabPanel value={value} index={2}>
              {value === 2 && <MyAppointments />}
            </TabPanel>
          )}
          {currentUser?.user?.role[0].name === "ROLE_DENTIST" && (
            <TabPanel value={value} index={2}>
              {value === 2 && <MyCalendar />}
            </TabPanel>
          )}
        </main>
      </div>
    </>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.currentUser,
});

export default connect(mapStateToProps, null)(Home);
