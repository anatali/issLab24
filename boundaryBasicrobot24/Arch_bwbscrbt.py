### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('bwbscrbtArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxbwbscrbt', graph_attr=nodeattr):
          bwbrcore=Custom('bwbrcore','./qakicons/symActorSmall.png')
          bwobserver=Custom('bwobserver','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='obstacle', **evattr, decorate='true', fontcolor='darkgreen') >> bwbrcore
     bwbrcore >> Edge(color='magenta', style='solid', decorate='true', label='<engage<font color="darkgreen"> engagedone engagerefused</font> &nbsp; step<font color="darkgreen"> stepdone stepfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot
     basicrobot >> Edge(color='blue', style='solid',  decorate='true', label='<brdata &nbsp; >',  fontcolor='blue') >> bwobserver
     bwobserver >> Edge(color='blue', style='solid',  decorate='true', label='<pause &nbsp; >',  fontcolor='blue') >> bwbrcore
     bwbrcore >> Edge(color='blue', style='solid',  decorate='true', label='<cmd<font color="darkgreen"> cmddone cmdfailed</font> &nbsp; disengage &nbsp; >',  fontcolor='blue') >> basicrobot
diag
