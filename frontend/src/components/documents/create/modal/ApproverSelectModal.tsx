import React, {useEffect, useState} from "react";
import {Backdrop, Box, Card, Fade, List, ListItem, ListItemText, makeStyles, Modal, Paper} from "@material-ui/core";

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
  const [teams, setTeams] = useState<IApproverTeam[]>([])
  const [approvalCandidates, setApprovalCandidates] = useState<IApprover[]>([])
  const [approvers, setApprovers] = useState<IApprover[]>([])

  const handleClose = () => {
    setOpen(false);
  };

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

  const fetchApprovalCandidatesByTeam = (id: number) => {
    if (id === 1) {
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
        id: 1,
        jobPosition: 'TEAM_LEADER',
        jobPositionText: '팀장',
        teamName: '서비스개발팀',
        name: '박우빈2'
      },
      {
        id: 2,
        jobPosition: 'PART_MANAGER',
        jobPositionText: '파트장',
        teamName: '서비스개발팀',
        name: '닉우빈2'
      },
    ])
  }

  const addApprover = (approver: IApprover) => {
    if (approvers.includes(approver)) {
      return;
    }
    setApprovers([...approvers, approver])
  }

  useEffect(() => {
    fetchTeams();
  }, [])

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
                <li>새로운 결재 문서를 기안할 수 있습니다.</li>
                <li>문서는 제목과 분류, 내용을 필수로 입력해야 합니다.</li>
              </ul>
            </div>
            <Card elevation={0} className={classes.approvalCandidatesCard}>

              <Box p={2} className={classes.approvalCandidatesBox}>
                <div className={classes.approvalCandidatesTitle}>
                  팀 선택
                </div>
                <Paper className={classes.approvalCandidatesPaper}>
                  <List>
                    {
                      teams.map((item, index) => {
                        return (
                          <ListItem button key={index} onClick={() => {
                            fetchApprovalCandidatesByTeam(item.id)
                          }}>
                            <ListItemText primary={item.name}/>
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
                  <List>
                    {
                      approvalCandidates.map((item, index) => {
                        return (
                          <ListItem button key={index} onClick={() => {
                            addApprover(item)
                          }}>
                            <ListItemText primary={item.jobPositionText} style={{marginRight: '3px'}}/>
                            <ListItemText primary={item.name}/>
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
                  <List>
                    {
                      approvers.map((item, index) => {
                        return (
                          <ListItem button key={index} onClick={() => console.log(item.id)}>
                            <ListItemText primary={item.name}/>
                          </ListItem>
                        )
                      })
                    }
                  </List>
                </Paper>
              </Box>

            </Card>

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
    // maxHeight: '500px',
  },
  modalContents: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: '20px 20px 10px 20px',
    // width: '800px',
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
    fontSize: '20px',
    textAlign: 'center',
    padding: '0 0 10px 0',
  },
  approvalCandidatesPaper: {
    height: '300px',
    overflow: 'auto',
    minWidth: '115px',
  },
  approversCard: {
    backgroundColor: 'rgba(66, 151, 212, 0.08)',
    display: 'inline-box',
    marginLeft: '25px',
  },
  approversBox: {},
  approversTitle: {
    fontSize: '20px',
    textAlign: 'center',
    padding: '0 0 10px 0',
  },
  approversPaper: {
    height: '300px',
    width: '400px',
    overflow: 'auto',
  }
}));

export default ApproverSelectModal;
