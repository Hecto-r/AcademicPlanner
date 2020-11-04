import React, {Component} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({table: {minWidth: 800, maxWidth: 850},
});

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Dr. Behesti', 'mbeheshti@csudh.edu', '(310)-243-3398', 'NSM A 134', 'T/Th: 1pm - 2pm'),
  createData('Dr. Chatterjee', 'achatterjee@csudh.edu','(310)-243-3240','NSM E 113', 'W/TH: 6pm-7pm'),
  createData('Dr. Han','jhan@csudh.edu', '(310)-243-2624', 'NSM A 133', 'M/W: 10am - 11am'),
  createData('Dr. Hollister', 'bhollister@csudh.edu', '(310)-243-1023', 'NSM B 218', 'M/W: 5:30pm - 6:30pm'),
  createData('Dr. Izaddoost', 'aizaddoost@csuh.edu', '(310)-243-2873', 'NSM E 115', 'T/TH 10am-12pm'),
  createData('Dr. Salehin', 'ksalehin@csudh.edu', '(310)-243-3882', 'NSM E 107', 'T:5pm-5:50pm, 6-6:50pm'),
  createData('Dr. Suchenek', 'msuchenek@csudh.edu', '(310)-243-3398', 'NSM A 131', 'T: 8:30pm-9:30pm'),
  createData('Dr. Tang', 'btang@csudh.edu', '(310)-243-2510', 'NSM E 117', 'M/TH: 5:30pm-7pm'),
  createData('Dr. Zuo', 'lzuo@csudh.edu', '(310)-243-2510', 'NSM E 109', 'T/TH: 3:50pm-5:20pm')
];

export default function BasicTable() {
  const classes = useStyles();

  return (
          <div>
              <h1>CSUDH Advising Information Page</h1>
                <p1>If you have already chosen and submitted the professor you would like to have an appointment with 
                <br></br>then please do not enter another submission.
                </p1>
              <form>
                  <p><input type = 'text' placeholder='Your Name' name='name' /></p>
                  <p><button>Send Message</button></p>
              </form>
              <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="simple table" align='center'>
                  <TableHead>
                    <TableRow>
                      <TableCell>Professor Name</TableCell>
                      <TableCell align="center">Email</TableCell>
                      <TableCell align="center">Phone&nbsp;</TableCell>
                      <TableCell align="center">Office Location&nbsp;</TableCell>
                      <TableCell align="center">Office Hours&nbsp;(g)</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {rows.map((row) => (
                      <TableRow key={row.name}>
                        <TableCell component="th" scope="row">
                          {row.name}
                        </TableCell>
                        <TableCell align="center">{row.calories}</TableCell>
                        <TableCell align="center">{row.fat}</TableCell>
                        <TableCell align="center">{row.carbs}</TableCell>
                        <TableCell align="center">{row.protein}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
  );
}
