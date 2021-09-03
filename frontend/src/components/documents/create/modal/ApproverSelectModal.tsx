import React from "react";
import {Backdrop, Box, Card, Fade, List, ListItem, ListItemText, makeStyles, Modal, Paper} from "@material-ui/core";

interface IApproverSelectModal {
  value: string
  setValue: (value: string) => void
}

const ApproverSelectModal = (
  {
    value, setValue,
  }: IApproverSelectModal) => {

  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value)
  }

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const teams = [
    {
      id: 1,
      name: '정산시스템팀',
    },
    {
      id: 2,
      name: '서비스개발팀'
    },
  ]

  return (
    <>
      <Modal
        className={classes.modal}
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <div className={classes.modalContents}>
            <div className={classes.modalTitle}>
              결재자 지정
            </div>
            <Card elevation={0} className={classes.modalContentsCard}>
              <Box p={2}>
                <div>
                  <Paper style={{maxHeight: 200, overflow: 'auto'}}>
                    <List>
                      {
                        teams.map((item, index) => {
                          return (
                            <ListItem button key={index} onClick={() => console.log(item.id)}>
                              <ListItemText primary={item.name}/>
                            </ListItem>
                          )
                        })
                      }
                    </List>
                  </Paper>
                </div>
              </Box>
            </Card>
          </div>
        </Fade>
      </Modal>
    </>
  );
}

const useStyles = makeStyles(theme => ({
  inputRoot: {
    padding: '2px 4px',
    display: 'flex',
    alignItems: 'center',
  },
  input: {
    marginLeft: theme.spacing(1),
    flex: 1,
  },
  iconButton: {
    padding: 10,
  },
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalContents: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: '20px 20px 10px 20px',
    width: '800px',
    borderRadius: '5px',
    outline: 'none',
  },
  modalTitle: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '25px',
    padding: '0 0 20px 15px',
  },
  modalContentsCard: {
    backgroundColor: '#fafafa',
  },
}));

export default ApproverSelectModal;
