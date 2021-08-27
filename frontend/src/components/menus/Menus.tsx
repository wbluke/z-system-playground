import React from 'react';
import {List} from "@material-ui/core";
import InsertDriveFileIcon from '@material-ui/icons/InsertDriveFile';
import AssignmentIcon from '@material-ui/icons/Assignment';
import FlightTakeoffIcon from '@material-ui/icons/FlightTakeoff';
import NoteAddIcon from '@material-ui/icons/NoteAdd';
import AssignmentTurnedInIcon from '@material-ui/icons/AssignmentTurnedIn';
import ClearAllOutlinedIcon from '@material-ui/icons/ClearAllOutlined';
import AppMenuItem from "./AppMenuItem";

const appMenuItems = [
  {
    name: '문서 관리',
    icon: <InsertDriveFileIcon/>,
    items: [
      {
        name: '기안 문서함',
        link: '/outbox',
        icon: <NoteAddIcon/>
      },
      {
        name: '결재 문서함',
        link: '/inbox',
        icon: <AssignmentTurnedInIcon/>
      },
      {
        name: '완료 문서함',
        link: '/archive',
        icon: <ClearAllOutlinedIcon/>
      },
    ],
  },
  {
    name: '휴가 관리',
    icon: <AssignmentIcon/>,
    items: [
      {
        name: '휴가 신청',
        link: '/day-off',
        icon: <FlightTakeoffIcon/>
      },
    ],
  },
]

const MainMenus = () => {
  return (
    <>
      <List component="nav" disablePadding>
        {appMenuItems.map((item, index) => (
          <AppMenuItem {...item} key={index}/>
        ))}
      </List>
    </>
  );
}

export default MainMenus;
