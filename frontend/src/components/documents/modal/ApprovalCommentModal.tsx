import React, {useState} from "react";
import {Backdrop, Button, Card, CardContent, Fade, makeStyles, Modal, TextField} from "@material-ui/core";

export interface IApprovalCommentModalOpen {
  approvalUrl: string
  open: boolean
}

interface IApprovalCommentModal {
  modalOpen: IApprovalCommentModalOpen
  setModalOpen: (modalOpen: IApprovalCommentModalOpen) => void
  closeParent: () => void
}

const ApprovalCommentModal = (
  {
    modalOpen, setModalOpen, closeParent
  }: IApprovalCommentModal) => {

  const classes = useStyles();

  const [comment, setComment] = useState<string>('');

  const onConfirm = () => {
    // request

    handleClose();
    closeParent();

    window.location.reload();
  }

  const handleClose = () => {
    setModalOpen({approvalUrl: '', open: false});
    clear();
  };

  const clear = () => {
    setComment('');
  }

  return (
    <>
      <Modal
        className={classes.modal}
        open={modalOpen.open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={modalOpen.open}>
          <div className={classes.modalContents}>
            <div className={classes.modalTitle}>
              결재 의견
            </div>

            <Card elevation={0} className={classes.documentCardRoot}>
              <CardContent className={classes.documentCardContents}>

                <TextField
                  onChange={event => setComment(event.target.value)}
                  fullWidth
                  multiline
                  label="의견"
                  rows={5}
                  variant={"outlined"}
                />

              </CardContent>
            </Card>

            <div className={classes.confirmButtonSection}>
              <Button
                className={classes.confirmButton}
                variant="contained"
                color="primary"
                onClick={onConfirm}
              >
                확인
              </Button>
            </div>

          </div>
        </Fade>
      </Modal>
    </>
  );
}

const useStyles = makeStyles(theme => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalContents: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: '20px 20px 10px 20px',
    borderRadius: '5px',
    outline: 'none',
    maxHeight: '1700px',
    overflow: 'scroll',
  },
  modalTitle: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '25px',
    padding: '0 0 15px 15px',
  },
  modalTitleDescription: {
    padding: '0 0 20px 15px',
  },
  documentCardRoot: {
    minWidth: 500,
    backgroundColor: 'rgba(66, 151, 212, 0.08)',
  },
  documentCardContents: {
    padding: '8px',
    '&:last-child': {
      paddingBottom: '8px'
    }
  },
  confirmButtonSection: {
    textAlign: 'right',
  },
  confirmButton: {
    margin: '7px 0'
  },
}));

export default ApprovalCommentModal;
