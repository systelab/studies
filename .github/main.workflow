workflow "New workflow" {
  on = "push"
  resolves = ["mvn"]
}

action "mvn" {
  uses = "mvn"
  runs = "clean install"
}
