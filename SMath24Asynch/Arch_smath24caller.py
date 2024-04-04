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
with Diagram('smath24callerArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcaller', graph_attr=nodeattr):
          c1=Custom('c1','./qakicons/symActorSmall.png')
          callerobserver=Custom('callerobserver','./qakicons/symActorWithobjSmall.png')
     f=Custom('f','./qakicons/server.png')
     with Cluster('ctxsmath', graph_attr=nodeattr):
          smathasynch=Custom('smathasynch(ext)','./qakicons/externalQActor.png')
     f=Custom('f','./qakicons/server.png')
     c1 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> smathasynch
     f >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> smathasynch
     c1 >> Edge(color='blue', style='solid',  decorate='true', label='<callerinfo &nbsp; >',  fontcolor='blue') >> callerobserver
diag
