import React, { useEffect } from "react";
import Button from "@material-ui/core/Button";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import { connect } from "react-redux";
import { format } from "date-fns";
import Axios from "axios";

const MyAppointments = ({ currentUser }) => {
  const [appointments, setAppointments] = React.useState(null);

  useEffect(() => {
    (async () => {
      let userId = currentUser.user.id;
      const res = await Axios.get(`/api/appointment/mine-patient/${userId}`);
      let appointments = res.data;
      setAppointments(appointments);
    })();
  }, []);

  const handleCancelAppointment = async (e, id) => {
    e.preventDefault();
    console.log(id);
    const res = await Axios.delete(`/api/appointment/cancel/${id}`);
    let userId = currentUser.user.id;
    const resp = await Axios.get(`/api/appointment/mine-patient/${userId}`);
    let appointments = resp.data;
    setAppointments(appointments);
  };

  return (
    <div className="Tabela">
      {appointments && (
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>
                <b>Date from</b>
              </TableCell>
              <TableCell>
                <b>Duration</b>
              </TableCell>
              <TableCell></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {appointments &&
              appointments.map((appoint) => (
                <>
                  {console.log(appoint)}
                  <TableRow key={appoint.id}>
                    <TableCell>
                      {format(
                        new Date(appoint.startDate),
                        "MMMM dd, yyyy - HH:mm:ss"
                      )}
                    </TableCell>

                    <TableCell>{appoint.duration} min</TableCell>
                    <TableCell>
                      <Button
                        variant="contained"
                        onClick={(e) => handleCancelAppointment(e, appoint.id)}
                      >
                        Cancel
                      </Button>
                    </TableCell>
                  </TableRow>
                </>
              ))}
          </TableBody>
        </Table>
      )}
    </div>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.currentUser,
});

export default connect(mapStateToProps, null)(MyAppointments);
