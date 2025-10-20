// package frc.robot.opmodes.auto;

// import frc.robot.opmodes.Opmode;
// import frc.robot.states.LinearMachine;
// import frc.robot.states.StateMachineBuilder;
// import frc.robot.subsystems.Grinder;

// public class CenterAuto implements Opmode {
//     String state;
//     boolean enter = false, changed = true;
//     Grinder bot;

//     public void init() {
//         state = "START";
//         bot = new Grinder();
//     }

//     public void periodic() {
//         if(changed) {
//             changed = false;
//             enter = true;
//         }
//         /*
//          * forward
//          * drop l4 coral
//          * grab algae
//          * go to barge
//          * place in barge
//          * park
//          */
//         switch(state) {
//             case "START":
//                 to("FORWARD");
//                 break;
//             case "FORWARD":
//                 if(enter) {
//                     bot.base.setTarget(0, 10, 0);
//                 }
//                 bot.base.driveto();
//                 if(bot.base.ready()) to("LIFT");
//                 break;
//             case "LIFT":
//                 if(enter) {
//                     bot.arm.goTo(bot.arm.CORAL_L4);
//                 }
//                 bot.arm.update();
//                 if(bot.arm.ready()) to(...);
//         }

//         if(enter) enter = false;
//     }

//     public void to(String next) {
//         state = next;
//         changed = true;
//     }
// }
