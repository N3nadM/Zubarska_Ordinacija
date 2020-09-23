import React, { useEffect } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import moment from "moment";
import { connect } from "react-redux";
import Axios from "axios";

const localizer = momentLocalizer(moment);

const MyCalendar = ({ currentUser }) => {
  const [appointments, setAppointments] = React.useState(null);

  useEffect(() => {
    (async () => {
      let userId = currentUser.user.id;
      const res = await Axios.get(`/api/appointment/mine-dentist/${userId}`);
      let appointments = res.data;
      appointments.map(
        (appoint) => (appoint.startDate = new Date(appoint.startDate))
      );
      setAppointments(appointments);
    })();
  }, []);

  return (
    <div style={{ height: 700 }}>
      {appointments && (
        <Calendar
          style={{ maxHeight: "100%" }}
          events={appointments}
          startAccessor="startDate"
          showMultiDayTimes={false}
          localizer={localizer}
        />
      )}
    </div>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.currentUser,
});

export default connect(mapStateToProps, null)(MyCalendar);
