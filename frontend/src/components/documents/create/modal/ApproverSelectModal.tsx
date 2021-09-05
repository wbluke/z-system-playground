import React, {useEffect, useState} from "react";
import {
  Backdrop,
  Box,
  Button,
  Card,
  Fade,
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
  makeStyles,
  Modal,
  Paper
} from "@material-ui/core";
import DeleteIcon from '@material-ui/icons/Delete';

interface IApproverTeam {
  id: number
  name: string
}

interface IApprover {
  id: number
  jobPosition: string
  jobPositionText: string
  teamName: string
  name: string
}

interface IApproverSelectModal {
  setValue: (value: string) => void
  open: boolean
  setOpen: (open: boolean) => void
}

const ApproverSelectModal = (
  {
    setValue,
    open, setOpen
  }: IApproverSelectModal) => {

  const classes = useStyles();
  const [teams, setTeams] = useState<IApproverTeam[]>([]);
  const [approvalCandidates, setApprovalCandidates] = useState<IApprover[]>([]);
  const [approvers, setApprovers] = useState<IApprover[]>([]);
  const [selectedTeamId, setSelectedTeamId] = useState<number>(0);

  const fetchTeams = () => {
    setTeams([
      {
        id: 1,
        name: '정산시스템팀',
      },
      {
        id: 2,
        name: '서비스개발팀'
      },
    ])
  }

  const fetchApprovalCandidatesByTeam = (teamId: number) => {
    if (teamId === 1) {
      setApprovalCandidates([
        {
          id: 1,
          jobPosition: 'TEAM_LEADER',
          jobPositionText: '팀장',
          teamName: '정산시스템팀',
          name: '박우빈'
        },
        {
          id: 2,
          jobPosition: 'TEAM_MEMBER',
          jobPositionText: '팀원',
          teamName: '정산시스템팀',
          name: '닉우빈'
        },
      ])
      return
    }

    setApprovalCandidates([
      {
        id: 3,
        jobPosition: 'TEAM_LEADER',
        jobPositionText: '팀장',
        teamName: '서비스개발팀',
        name: '박우빈2'
      },
      {
        id: 4,
        jobPosition: 'PART_MANAGER',
        jobPositionText: '파트장',
        teamName: '서비스개발팀',
        name: '닉우빈2'
      },
    ])
  }

  const updateCandidatesTeam = (teamId: number) => {
    setSelectedTeamId(teamId)
  }

  const addApprover = (approver: IApprover) => {
    if (approvers.includes(approver)) {
      return;
    }
    setApprovers([...approvers, approver])
  }

  const removeApprover = (approverId: number) => {
    setApprovers(
      approvers.filter(approver => approver.id !== approverId)
    )
  }

  const handleClose = () => {
    setOpen(false);
    clear();
  };

  const clear = () => {
    setTeams([]);
    setApprovalCandidates([]);
    setApprovers([]);
    setSelectedTeamId(0);
  }

  useEffect(() => {
    if (open) {
      fetchTeams();
    }
  }, [open])

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
            <div className={classes.modalTitleDescription}>
              <ul>
                <li>결재자를 승인 순서대로 지정할 수 있습니다.</li>
              </ul>
            </div>
            <Card elevation={0} className={classes.approvalCandidatesCard}>

              <Box p={2} className={classes.approvalCandidatesBox}>
                <div className={classes.approvalCandidatesTitle}>
                  팀 선택
                </div>
                <Paper className={classes.approvalCandidatesPaper}>
                  <List dense>
                    {
                      teams.map((item, index) => {
                        return (
                          <ListItem
                            button
                            key={index}
                            onClick={() => {
                              fetchApprovalCandidatesByTeam(item.id);
                              updateCandidatesTeam(item.id)
                            }}
                            style={{backgroundColor: (item.id === selectedTeamId) ? 'aliceblue' : ''}}
                          >
                            <ListItemText primary={item.name} className={classes.approvalTeamName}/>
                          </ListItem>
                        )
                      })
                    }
                  </List>
                </Paper>
              </Box>

              <Box p={2} className={classes.approvalCandidatesBox}>
                <div className={classes.approvalCandidatesTitle}>
                  결재자 선택
                </div>
                <Paper className={classes.approvalCandidatesPaper}>
                  <List dense className={classes.approvalCandidatesList}>
                    {
                      approvalCandidates.map((item, index) => {
                        return (
                          <ListItem button key={index} onClick={() => {
                            addApprover(item)
                          }}>
                            <ListItemText
                              primary={item.jobPositionText}
                              className={classes.approvalCandidateJobPosition}
                            />
                            <ListItemText primary={item.name} className={classes.approvalCandidateName}/>
                          </ListItem>
                        )
                      })
                    }
                  </List>
                </Paper>
              </Box>

            </Card>

            <Card elevation={0} className={classes.approversCard}>

              <Box p={2} className={classes.approversBox}>
                <div className={classes.approversTitle}>
                  결재라인
                </div>
                <Paper className={classes.approversPaper}>
                  <List dense>
                    {
                      approvers.map((item, index) => {
                        return (
                          <ListItem button key={index}>
                            <ListItemText primary={index + 1}/>
                            <ListItemText primary={item.teamName} className={classes.approverTeamName}/>
                            <ListItemText primary={item.jobPositionText} className={classes.approverJobPositionText}/>
                            <ListItemText primary={item.name} className={classes.approverName}/>
                            <ListItemSecondaryAction>
                              <IconButton
                                edge="end"
                                onClick={() => {
                                  removeApprover(item.id)
                                }}
                              >
                                <DeleteIcon/>
                              </IconButton>
                            </ListItemSecondaryAction>
                          </ListItem>
                        )
                      })
                    }
                  </List>
                </Paper>
              </Box>

            </Card>

            <div className={classes.confirmButtonArea}>
              <Button
                variant="contained"
                color="primary"
                onClick={() => {
                }}
              >
                완료
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
    maxHeight: '700px',
    overflow: 'scroll',
  },
  modalTitle: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '25px',
    padding: '0 0 5px 15px',
  },
  modalTitleDescription: {
    padding: '0 0 20px 15px',
  },
  approvalCandidatesCard: {
    backgroundColor: '#fafafa',
    display: 'inline-box',
  },
  approvalCandidatesBox: {},
  approvalCandidatesTitle: {
    fontSize: '15px',
    textAlign: 'center',
    padding: '0 0 10px 0',
  },
  approvalCandidatesPaper: {
    height: '300px',
    overflow: 'auto',
    minWidth: '115px',
  },
  approvalCandidatesList: {
    minWidth: '150px',
  },
  approvalTeamName: {},
  approversCard: {
    backgroundColor: 'rgba(66, 151, 212, 0.08)',
    display: 'inline-box',
    marginLeft: '25px',
  },
  approversBox: {},
  approversTitle: {
    fontSize: '15px',
    textAlign: 'center',
    padding: '0 0 10px 0',
  },
  approversPaper: {
    height: '300px',
    width: '400px',
    overflow: 'auto',
  },
  approvalCandidateJobPosition: {
    minWidth: '60px',
  },
  approvalCandidateName: {
    minWidth: '50px',
  },
  approverTeamName: {
    minWidth: '100px',
  },
  approverJobPositionText: {
    minWidth: '100px',
  },
  approverName: {
    minWidth: '100px',
  },
  confirmButtonArea: {
    textAlign: 'right',
    padding: '10px 0 10px 0',
  }
}));

export default ApproverSelectModal;