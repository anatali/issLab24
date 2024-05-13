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
with Diagram('bw24Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxbw24', graph_attr=nodeattr):
          bw24core=Custom('bw24core','./qakicons/symActorSmall.png')
          bwobserver=Custom('bwobserver','./qakicons/symActorSmall.png')
     with Cluster('ctxvrqak', graph_attr=nodeattr):
          vrqak=Custom('vrqak(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='vrinfo', **evattr, decorate='true', fontcolor='darkgreen') >> bwobserver
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> bwobserver
     sys >> Edge( label='obstacle', **evattr, decorate='true', fontcolor='darkgreen') >> bwobserver
     bw24core >> Edge(color='magenta', style='solid', decorate='true', label='<step<font color="darkgreen"> stepdone stepfailed</font> &nbsp; >',  fontcolor='magenta') >> vrqak
     vrqak >> Edge(color='blue', style='solid',  decorate='true', label='<vrinfo &nbsp; >',  fontcolor='blue') >> bw24core
     bw24core >> Edge(color='blue', style='solid',  decorate='true', label='<move &nbsp; >',  fontcolor='blue') >> vrqak
     bwobserver >> Edge(color='blue', style='solid',  decorate='true', label='<stop &nbsp; >',  fontcolor='blue') >> bw24core
diag
