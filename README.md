playground
==========

Little things that make life easier with Play! webapps

  lazy val playground = RootProject(uri("git://github.com/ybr/playground.git[#branch/tag]"))

  lazy val app = play.Project("playAppName", ...).dependsOn(playground)
